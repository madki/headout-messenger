import ratpack.http.Request;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;
import ratpack.groovy.template.TextTemplateModule;
import ratpack.guice.Guice;

import static ratpack.groovy.Groovy.groovyTemplate;

import java.util.*;

public class Main {
    private static final String TOKEN = "my_awesome_token";
    private static final String PAGE_ACCESS_TOKEN = "EAAIHDdA9sE0BAHbbn6aiLK2kF7O89BUGG3tEXe74cuCgq970jLRfdcxbR83UMTQ3q0JbbzZCyujPTzKaD733wrRWNkyUCwlMe5uMbZC2WGPg8WvQaytEH6ECCpLg5qJIhtsjllBiqzcTQUBWo5um9zvV1GR86eZB7OLoYzWIAZDZD";

    public static void main(String... args) throws Exception {
        RatpackServer.start(s -> s
                .serverConfig(c -> c
                        .baseDir(BaseDir.find())
                        .env())

                .registry(Guice.registry(b -> b
                        .module(TextTemplateModule.class, conf -> conf.setStaticallyCompile(true))))

                .handlers(chain -> chain
                        .get(ctx -> ctx.render(groovyTemplate("index.html")))

                        .get("webhook", ctx -> {
                            Request request = ctx.getRequest();
                            Map<String, String> queryParams = request.getQueryParams();
                            if ("subscribe".equals(queryParams.get("hub.mode")) && TOKEN.equals(queryParams.get("hub.verify_token"))) {
                                ctx.render(queryParams.get("hub.challenge"));
                            } else {
                                ctx.render("Hello world!");
                            }
                        })

                        .files(f -> f.dir("public"))
                )
        );
    }
}
