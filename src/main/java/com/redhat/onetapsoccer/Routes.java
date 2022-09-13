
package com.redhat.onetapsoccer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.net.ssl.HostnameVerifier;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.http.conn.ssl.NoopHostnameVerifier;

@ApplicationScoped
public class Routes extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        routeTemplate("prometheusTemplate")
            .templateParameter("query")
            .templateParameter("metric")
            .from("rest:get:metrics/{{metric}}")
            .setHeader("Authorization", constant("Bearer {{grafana.sa.token}}"))
            .removeHeader(Exchange.HTTP_PATH)
            .recipientList(simple("{{prometheus.schema}}:{{prometheus.host}}/api/v1/query" +
            "?query={{query}}&bridgeEndpoint=true&x509HostnameVerifier=NoopHostnameVerifier"))
            .unmarshal().json(JsonLibrary.Jackson)
            .log("Received : \"${body}\"")
            .to("velocity:prometheus.vm?contentCache=true")
            .log("Converted : \"${body}\"")
            ;
        ;

        templatedRoute("prometheusTemplate")
            .parameter("metric", "goals-rate")
            .parameter("query", "sum(com_redhat_onetapsoccer_goal_total)/sum(com_redhat_onetapsoccer_shoot_total)");
        
        templatedRoute("prometheusTemplate")
            .parameter("metric", "goals")
            .parameter("query", "sum(com_redhat_onetapsoccer_goal_total)");
        
        templatedRoute("prometheusTemplate")
            .parameter("metric", "game-start")
            .parameter("query", "sum(com_redhat_onetapsoccer_game_start_total)");
        
        templatedRoute("prometheusTemplate")
            .parameter("metric", "ground-collision")
            .parameter("query", "sum(com_redhat_onetapsoccer_ground_collision_total)");
        
        templatedRoute("prometheusTemplate")
            .parameter("metric", "pole-collision")
            .parameter("query", "sum(com_redhat_onetapsoccer_pole_collision_total)");

        templatedRoute("prometheusTemplate")
            .parameter("metric", "shoot")
            .parameter("query", "sum(com_redhat_onetapsoccer_shoot_total)");

        templatedRoute("prometheusTemplate")
            .parameter("metric", "game-over")
            .parameter("query", "sum(com_redhat_onetapsoccer_game_over_total)");

        templatedRoute("prometheusTemplate")
            .parameter("metric", "head-collision")
            .parameter("query", "sum(com_redhat_onetapsoccer_head_collision_total)");

        templatedRoute("prometheusTemplate")
            .parameter("metric", "goals-per-player")
            .parameter("query", "sum by (player) (com_redhat_onetapsoccer_total{\"kind\"=\"goal\"})");

        templatedRoute("prometheusTemplate")
            .parameter("metric", "total-events")
            .parameter("query", "sum(com_redhat_onetapsoccer_total)");

        templatedRoute("prometheusTemplate")
            .parameter("metric", "unique-users")
            .parameter("query", "count(count by (user) (com_redhat_onetapsoccer_game_start_total))");
            
            //Dont know why do not work
            // templatedRoute("prometheusTemplate")
            // .parameter("metric", "total-collisions")
            // .parameter("query", "sum(com_redhat_onetapsoccer_pole_collision_total) + sum(com_redhat_onetapsoccer_head_collision_total) + sum(com_redhat_onetapsoccer_ground_collision_total)");

            //Work however need new velocity template
            // templatedRoute("prometheusTemplate")
            // .parameter("metric", "total-events-per-kind")
            // .parameter("query", "sum by (kind) (com_redhat_onetapsoccer_total)");

            //Dont know why do not work
            // templatedRoute("prometheusTemplate")
            // .parameter("metric", "ranking-top-10")
            // .parameter("query", "topk(10, max by (userName) (com_redhat_onetapsoccer_score))");

    }

    @Produces @Named("NoopHostnameVerifier")
	public HostnameVerifier foo() {
	    return new NoopHostnameVerifier();
	}
}