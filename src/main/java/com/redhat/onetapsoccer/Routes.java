
package com.redhat.onetapsoccer;

import javax.enterprise.context.ApplicationScoped;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

@ApplicationScoped
public class Routes extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        routeTemplate("prometheusTemplate")
            .templateParameter("query")
            .templateParameter("metric")
            .from("rest:get:metrics/{{metric}}")
            .removeHeader(Exchange.HTTP_PATH)
            .recipientList(simple("http:{{prometheus.host}}/api/v1/query" +
            "?query={{query}}&bridgeEndpoint=true"))
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
        

        
    }
}