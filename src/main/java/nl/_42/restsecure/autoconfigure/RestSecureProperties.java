package nl._42.restsecure.autoconfigure;

import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("rest-secure")
public final class RestSecureProperties {

    /**
     * Mappings from crowd group name to application role.
     */
    private Map<String, String> authorityToCrowdGroupMappings = new ConcurrentHashMap<>();
    /**
     * Crowd client properties.
     */
    private Properties crowdProperties = new Properties();

    public Set<Entry<String, String>> getCrowdGroupToAuthorityMappings() {
        return authorityToCrowdGroupMappings.entrySet()
                .stream()
                .collect(toMap(Entry::getValue, Entry::getKey))
                .entrySet();
    }
    
    public Map<String, String> getAuthorityToCrowdGroupMappings() {
        return authorityToCrowdGroupMappings;
    }

    public Properties getCrowdProperties() {
        return crowdProperties;
    }
}
