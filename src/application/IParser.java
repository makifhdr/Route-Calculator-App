package application;

import java.util.List;

public interface IParser {
	List<Durak> parseStations(String filePath);
	Taxi parseTaxi(String filePath);
}
