# ReactivitApp

This is an application, that instantiates Project Reactor work principles.

# ResourceLimiting

Application may be pretty high-loaded, and some intensive requests may take some time due to external communication.
Other libraries like resilience4j may limit only number of requests that may be accepted in a specified timespan.
So these requests may stash in processing and clutter application resources. The more resources is taken, the longer will take any additional request;
in the end it will lead to application failure, a lot of requests will be failed, new application deploy takes time, also newly deployed application is not so performing as warmed-up one.
That's why we developed ResourceLimiter©. ResourceLimiter controls number of requests in processing and doesn't allow it to exceed configured threshold. 
Furthermore, it is pretty flexible and allows to specify for control as many abstract application resources as you wish, 
manage them for specific endpoints consumption and configure without application rebuild.

Configuration example:
```yaml
app.resource-limiting:
  resource-capacity:
    WEBCLIENT_CONNECTIONS: 50
    CPU: 100
  endpoint-limits:
    "[GET /testing/first]":
      -
        resource: REDIS_CONNECTIONS
        personal-limit: 30
      -
        resource: CPU
        cost: 5
    "[GET /testing/{second}/hit]":
      -
        resource: REDIS_CONNECTIONS
        personal-limit: 20
    "[POST /testing/{second}/hit]":
      -
        resource: MEMORY
        personal-limit: 17
        cost: 1
      -
        resource: CPU
```

- **resource-capacity** defines resoures on application level (all resources are just abstract representation of this or that bottleneck, they're defined in code by simple enum). All endpoints that consume this resource (this must be specified in a set of consumed by endpoint resources, see below) will consume it from here if it is specified here.
- **endpoint-limits** defines personal endpoint limits for specified resource and amount of specified resource for this endpoit that will be consumed to process a single request, both on application and personal level (weight in other words).


- Endpoints are specified in common way with {pathVariables} as path variables. HTTP methods matter. `POST /hit` and `GET /hit` are different endpoints. HTTP method and path must be separated with a single space character.
- By default, if you don't specify any configuration no endpoints are limited and even no endpoints are counted. Everything is lazy: if you don't configure it, it won't be created. (Except ApplicationResource enums - they are plain enums in the source code).
- Resource can be limited both on application and personal endpoint levels. On application level resource and its limit will be shared among all endpoints that consume it.
- If you limit resource both on application and endpoint level, both of limits are considered, endpoint limit first.
- For a resource consumed by a single endpoint doesn't make sence to define it on application level. But you can define it both on endpoint and application level and it doesn't change anything, except application-level defined resource can be acquired by another endpoints.
- Endpoints can be limited to consume multiple resources - they all will be considered.
- Cost, being assigned, defines cost both on application and endpoint levels. If cost is not specified cost is 1.
- Since resources are plain enums they are ordered and acquired always in the same order for all endpoints and released always in reverse order, that prevents live locks.
- Configuration is a part of plain old application.yaml. Endpoints can be configured in it or with environment variables, but to add new resources you need to enrich ApplicationResource enum in the code.
- Iteration over endpoints takes O(n), where n - number of endpoints, so be careful with it.

