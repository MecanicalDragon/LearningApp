package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.recorder.internal.bouncycastle.util.encoders.Base64

import java.util.concurrent.ThreadLocalRandom

class ReactivitRunner extends Simulation {

  val reactivitUrl = "http://localhost:34344";

  def getRandomString: String = {
    val random = ThreadLocalRandom.current
    val r = new Array[Byte](8)
    random.nextBytes(r)
    Base64.toBase64String(r)
  }

  private val feeder = (1 to 10000).map(i => {
    val string = getRandomString
    Map(
      "id" -> i,
      "name" -> string,
      "surname" -> string
    )
  }).queue

  object PerformanceTest {
    val req = feed(feeder)
      .exec(request => {
        val count = request("id").as[Int]
        if (count % 1000 == 0) {
          printf("JOIN session # %s \n", request("id").as[String])
        }
        request
      })
      .exec(
        http("request")
          .post("/post")
          .header("Content-Type", "application/json")
          .body(StringBody(
            s"""
               |{
               |    "name": "#{name}",
               |    "surname": "#{surname}"
               |}
               |""".stripMargin))
          .check(jsonPath("$.seniority").is("#{name}"))
          .check(jsonPath("$.duty").is("#{surname}"))
      )
  }

  val perfTest = scenario("perfTest").exec(PerformanceTest.req)

  val httpProtocol = http.baseUrl(reactivitUrl)

  setUp(List(
    perfTest.inject(rampUsers(500).during(10)),
  )).protocols(httpProtocol)

}