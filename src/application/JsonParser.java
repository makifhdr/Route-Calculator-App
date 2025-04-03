package application;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser implements IParser {
	
	@Override
	public List<Durak> parseStations(String filePath) {
		try {
            ObjectMapper objectMapper = new ObjectMapper();
            
            // Read entire JSON file as a tree
            JsonNode rootNode = objectMapper.readTree(new File(filePath));

            // Extract "duraklar" array
            JsonNode duraklarNode = rootNode.get("duraklar");

            // Convert JSON array into List<Station>
            return objectMapper.readValue(duraklarNode.toString(), new TypeReference<List<Durak>>() {});

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}
	
	@Override
	public Taxi parseTaxi(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            City city = objectMapper.readValue(new File(filePath), City.class);
            return city.getTaxi();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
