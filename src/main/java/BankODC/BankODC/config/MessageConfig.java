package BankODC.BankODC.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.StaticMessageSource;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

@Configuration
public class MessageConfig {

    @Bean
    public StaticMessageSource messageSource() {
        StaticMessageSource messageSource = new StaticMessageSource();

       
        loadYamlMessages("classpath:i18n/messages_fr.yml", java.util.Locale.FRENCH, messageSource);

      
        loadYamlMessages("classpath:i18n/messages_en.yml", java.util.Locale.ENGLISH, messageSource);

        return messageSource;
    }

    private void loadYamlMessages(String resourcePath, java.util.Locale locale, StaticMessageSource messageSource) {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            Properties props = new Properties();

            try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath.substring(10))) { // Remove "classpath:"
                if (is != null) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> yamlMap = mapper.readValue(is, Map.class);
                    flattenYaml("", yamlMap, props);

                   
                    for (String key : props.stringPropertyNames()) {
                        messageSource.addMessage(key, locale, props.getProperty(key));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading YAML messages from " + resourcePath + ": " + e.getMessage());
        }
    }

    private void flattenYaml(String prefix, Map<String, Object> map, Properties props) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = prefix.isEmpty() ? entry.getKey() : prefix + "." + entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> nestedMap = (Map<String, Object>) value;
                flattenYaml(key, nestedMap, props);
            } else {
                props.setProperty(key, value.toString());
            }
        }
    }
}