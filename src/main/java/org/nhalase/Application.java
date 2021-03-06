package org.nhalase;

import org.gmjm.slack.api.hook.HookRequest;
import org.gmjm.slack.api.hook.HookRequestFactory;
import org.gmjm.slack.api.message.SlackMessageFactory;
import org.gmjm.slack.core.hook.HttpsHookRequestFactory;
import org.gmjm.slack.core.message.JsonMessageFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Value("${slack.webhook.url}")
    private String slackWebhookUrl;

    @Bean
    public HookRequestFactory getHookRequestFactory() {
        return new HttpsHookRequestFactory();
    }

    @Bean
    public HookRequest getHookRequest(HookRequestFactory hookRequestFactory) {
        return hookRequestFactory.createHookRequest(slackWebhookUrl);
    }

    @Bean
    public SlackMessageFactory getSlackMessageFactory() {
        return new JsonMessageFactory();
    }

}
