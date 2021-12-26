# Module misc-g-app

Here we instantiate actuator work and metrics gathering:
- `application.yml` info section provides some custom information about application.
- `CustomInfoContributor` depicts, how to provide some inner-application metrics to actuator endpoints.
- `prometheus.yml` is prometheus config to collect metrics from this app. Metrics graph can be watched [here](http://localhost:9090/graph)
- You always can see collectable metrics at `http://localhost:8464/actuator/prometheus`
- [Prometheus Documentation](https://prometheus.io/docs/prometheus/latest/getting_started/)
  and [Scrape config](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#scrape_config)
- [Overall Guide](https://www.mokkapps.de/blog/monitoring-spring-boot-application-with-micrometer-prometheus-and-grafana-using-custom-metrics/)

**Grafana usage**

1. Start Grafana: `docker run -d -p 3000:3000 grafana/grafana`
2. Grafana is accessible via `localhost:3000`
3. To access localhost prometheus from docker container specify `host.docker.internal` instead of `localhost`
4. Import JVM dashboard `https://grafana.com/grafana/dashboards/4701`
5. Create custom dashboard, exporting `Misc-G-App Business Metrics.json` from resources
6. [Grafana Dashboards Tutorial](https://grafana.com/tutorial/)
7. [Grafana query functions](https://prometheus.io/docs/prometheus/latest/querying/functions/)
