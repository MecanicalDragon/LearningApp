package simkt

import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl.http
import io.gatling.javaapi.http.HttpDsl.status

class SampleKtSimulation : Simulation() {

    val feeder = csv("coders.csv").random()
    val baseUrl = "http://localhost:34344/gatling"

    val seniorityCheck =
        feed(feeder)
            .exec(
                http(baseUrl)
                    .get("/coder?seniority=#{seniority}")
                    .check(
                        status().shouldBe(200),
                        jsonPath("$.name").shouldBe { it.get("name") },
                        jsonPath("$.surname").shouldBe { it.get("surname") },
                        jsonPath("$.name").saveAs("respName"),
                        jsonPath("$.surname").saveAs("respSurname")
                    )
            )
//            .pause(1)
            .exec(
                http(baseUrl)
                    .post("/seniority")
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .body(
                        StringBody(
                            """
                        { 
                            "name": "#{respName}",
                            "surname": "#{respSurname}" 
                        }
                        """.trimIndent()
                        )
                    )
                    .check(
                        status().shouldBe(200),
                        bodyString().shouldBe { it.get("seniority") }
                    )
            )

    val repeated =
        repeat(30, "attempt").on(
            exec(
                http(baseUrl)
                    .get("/number?index=#{attempt}")
                    .check(
                        status().shouldBe(200)
                    )
            ).pause(1)
        )

    val httpProtocol =
        http.baseUrl(baseUrl)
            .acceptHeader("application/json;q=0.9,*/*;q=0.8")
            .contentTypeHeader("application/json,*/*")

    val coders = scenario("Seniority Check").exec(seniorityCheck)
    val perf = scenario("Performance Check").exec(seniorityCheck, repeated)

    init {
        setUp(
            coders.injectOpen(rampUsers(1000).during(10)),
            perf.injectOpen(rampUsers(100).during(10))
        ).protocols(httpProtocol)
    }
}
