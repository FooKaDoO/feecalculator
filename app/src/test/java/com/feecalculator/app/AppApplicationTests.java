package com.feecalculator.app;

import com.feecalculator.app.controller.FeeController;
import com.feecalculator.app.datafetch.DatabaseInit;
import com.feecalculator.app.datafetch.ScheduledTasks;
import com.feecalculator.app.dto.IWeatherTypes.IPhenomenon;
import com.feecalculator.app.entity.XML.Observations;
import com.feecalculator.app.enums.PhenomenonEnum;
import com.feecalculator.app.enums.StationEnum;
import com.feecalculator.app.enums.VehicleEnum;
import com.feecalculator.app.enums.WeatherEnum;
import com.feecalculator.app.error.NotAllowedError;
import com.feecalculator.app.repository.FeeRepository;
import com.feecalculator.app.repository.StationRepository;
import com.feecalculator.app.service.DatafetchService;
import com.feecalculator.app.service.FeeService;
import com.feecalculator.app.service.StationService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootTest
class AppApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	DatafetchService datafetchService;
	@Autowired
	StationService stationService;
	@Autowired
	DatabaseInit databaseInit;
	@Autowired
	ScheduledTasks scheduledTasks;

	@Value("${custom.URI}")
	private String URI;
	@Test
	void testDataFetch() throws Exception {
		String XMLData = datafetchService.fetchDataFromURI(URI);
		assert XMLData.contains("observations");
		Observations observations = datafetchService.mapXMLtoEntity(XMLData);
		assert observations != null;
		assert observations.getTimestamp() != null;
		assert observations.getStation().size() > 0;
		assert observations.getStation().get(0) != null;

		long amountOfData = stationService.getCountOfDatabaseEntries();
		databaseInit.getData();
		assert amountOfData == stationService.getCountOfDatabaseEntries();

		scheduledTasks.getData();
		assert amountOfData != stationService.getCountOfDatabaseEntries();
	}


	@Test
	void testEnums() {
		assert PhenomenonEnum.SNOW.equals(PhenomenonEnum.fromName("snow"));
		assert PhenomenonEnum.SLEET.equals(PhenomenonEnum.fromName("sleet"));
		assert PhenomenonEnum.RAIN.equals(PhenomenonEnum.fromName("rain"));
		assert PhenomenonEnum.RAIN.equals(PhenomenonEnum.fromName("shower"));
		assert PhenomenonEnum.GLAZE.equals(PhenomenonEnum.fromName("glaze"));
		assert PhenomenonEnum.HAIL.equals(PhenomenonEnum.fromName("hail"));
		assert PhenomenonEnum.THUNDER.equals(PhenomenonEnum.fromName("thunder"));
		assert PhenomenonEnum.fromName("a") == null;


		assert StationEnum.TALLINN.equals(StationEnum.fromWMOCode(26038));
		assert StationEnum.TARTU.equals(StationEnum.fromWMOCode(26242));
		assert StationEnum.PÄRNU.equals(StationEnum.fromWMOCode(41803));
		assert StationEnum.fromWMOCode(0) == null;

		assert StationEnum.TALLINN.equals(StationEnum.fromName("tallinn"));
		assert StationEnum.TARTU.equals(StationEnum.fromName("tartu"));
		assert StationEnum.PÄRNU.equals(StationEnum.fromName("pärnu"));
		assert StationEnum.fromName("a") == null;


		assert VehicleEnum.CAR.equals(VehicleEnum.fromName("car"));
		assert VehicleEnum.SCOOTER.equals(VehicleEnum.fromName("scooter"));
		assert VehicleEnum.BIKE.equals(VehicleEnum.fromName("bike"));
		assert VehicleEnum.fromName("a") == null;


		assert WeatherEnum.TEMPERATURE.equals(WeatherEnum.fromName("temp"));
		assert WeatherEnum.TEMPERATURE.equals(WeatherEnum.fromName("atef"));

		assert WeatherEnum.WINDSPEED.equals(WeatherEnum.fromName("wind"));
		assert WeatherEnum.WINDSPEED.equals(WeatherEnum.fromName("speed"));
		assert WeatherEnum.WINDSPEED.equals(WeatherEnum.fromName("wsef"));

		assert WeatherEnum.PHENOMENON.equals(WeatherEnum.fromName("phenomenon"));
		assert WeatherEnum.PHENOMENON.equals(WeatherEnum.fromName("wpef"));

		assert WeatherEnum.fromName("a") == null;
	}

	@Autowired
	FeeRepository feeRepository;
	@Test
	void testFeeRepository() throws NotAllowedError {
		assert 4.0 == feeRepository.getStationFee(VehicleEnum.CAR, StationEnum.TALLINN);
		assert 3.5 == feeRepository.getStationFee(VehicleEnum.SCOOTER, StationEnum.TALLINN);
		assert 3.0 == feeRepository.getStationFee(VehicleEnum.BIKE, StationEnum.TALLINN);

		assert 3.5 == feeRepository.getStationFee(VehicleEnum.CAR, StationEnum.TARTU);
		assert 3.0 == feeRepository.getStationFee(VehicleEnum.SCOOTER, StationEnum.TARTU);
		assert 2.5 == feeRepository.getStationFee(VehicleEnum.BIKE, StationEnum.TARTU);

		assert 3.0 == feeRepository.getStationFee(VehicleEnum.CAR, StationEnum.PÄRNU);
		assert 2.5 == feeRepository.getStationFee(VehicleEnum.SCOOTER, StationEnum.PÄRNU);
		assert 2.0 == feeRepository.getStationFee(VehicleEnum.BIKE, StationEnum.PÄRNU);


		assert 1.0 == feeRepository.getTemperatureFee(VehicleEnum.SCOOTER, -15.0);
		assert 1.0 == feeRepository.getTemperatureFee(VehicleEnum.BIKE, -15.0);

		assert 0.5 == feeRepository.getTemperatureFee(VehicleEnum.SCOOTER, -5.0);
		assert 0.5 == feeRepository.getTemperatureFee(VehicleEnum.BIKE, -5.0);


		assert 0.5 == feeRepository.getWindSpeedFee(VehicleEnum.BIKE, 15.0);
		String err = "";
		try {
			feeRepository.getWindSpeedFee(VehicleEnum.BIKE, 25.0);
		} catch (NotAllowedError e) {
			err = e.getMessage();
		}
		assert err.equals("Usage of selected vehicle type is forbidden");


		assert 1.0 == feeRepository.getPhenomenonFee(VehicleEnum.SCOOTER, PhenomenonEnum.SLEET);
		assert 1.0 == feeRepository.getPhenomenonFee(VehicleEnum.BIKE, PhenomenonEnum.SLEET);

		assert 1.0 == feeRepository.getPhenomenonFee(VehicleEnum.SCOOTER, PhenomenonEnum.SNOW);
		assert 1.0 == feeRepository.getPhenomenonFee(VehicleEnum.BIKE, PhenomenonEnum.SNOW);

		assert 0.5 == feeRepository.getPhenomenonFee(VehicleEnum.SCOOTER, PhenomenonEnum.RAIN);
		assert 0.5 == feeRepository.getPhenomenonFee(VehicleEnum.BIKE, PhenomenonEnum.RAIN);


		List.of(PhenomenonEnum.GLAZE, PhenomenonEnum.HAIL, PhenomenonEnum.THUNDER)
				.forEach(phenomenon -> {
					String errMsg = "";
					try {
						feeRepository.getPhenomenonFee(VehicleEnum.SCOOTER, phenomenon);
					} catch (NotAllowedError e) {
						errMsg = e.getMessage();
					}
					assert errMsg.equals("Usage of selected vehicle type is forbidden");
					try {
						feeRepository.getPhenomenonFee(VehicleEnum.BIKE, phenomenon);
					} catch (NotAllowedError e) {
						errMsg = e.getMessage();
					}
					assert errMsg.equals("Usage of selected vehicle type is forbidden");
				});


		Double old1 = feeRepository.getStationFee(VehicleEnum.CAR, StationEnum.TALLINN);
		Double old2 = feeRepository.getPhenomenonFee(VehicleEnum.CAR, PhenomenonEnum.RAIN);
		Double old3 = feeRepository.getTemperatureFee(VehicleEnum.CAR,-5.0);
		Double old4 = feeRepository.getWindSpeedFee(VehicleEnum.CAR, 15.0);

		feeRepository.setStationFee(VehicleEnum.CAR, StationEnum.TALLINN, 99.0);
		feeRepository.setPhenomenonFee(VehicleEnum.CAR, PhenomenonEnum.RAIN, 99.0);
		feeRepository.setTemperatureFee(VehicleEnum.CAR,-5.0, 99.0);
		feeRepository.setWindSpeedFee(VehicleEnum.CAR, 15.0, 99.0);

		assert 99.0 == feeRepository.getStationFee(VehicleEnum.CAR, StationEnum.TALLINN);
		assert 99.0 == feeRepository.getPhenomenonFee(VehicleEnum.CAR, PhenomenonEnum.RAIN);
		assert 99.0 == feeRepository.getTemperatureFee(VehicleEnum.CAR,-5.0);
		assert 99.0 == feeRepository.getWindSpeedFee(VehicleEnum.CAR, 15.0);

		feeRepository.setStationFee(VehicleEnum.CAR, StationEnum.TALLINN, old1);
		feeRepository.setPhenomenonFee(VehicleEnum.CAR, PhenomenonEnum.RAIN, old2);
		feeRepository.setTemperatureFee(VehicleEnum.CAR,-5.0, old3);
		feeRepository.setWindSpeedFee(VehicleEnum.CAR, 15.0, old4);
	}

	@Autowired
	FeeService feeService;
	@Test
	void testFeeService() {
		assert feeService.setFee("city", "tallinn", "car", 1.0);
		assert feeService.getFee("tallinn", "car", Long.MAX_VALUE) == 1.0;
		feeService.setFee("city", "tallinn", "car", 4.0);
	}

	@Autowired
	StationRepository stationRepository;

	@Test
	void testStationRepository() {
		assert stationRepository.findFirstByNameBeforeTimestamp("Tallinn-Harku", Long.MAX_VALUE) != null;
	}

	@Autowired
	FeeController feeController;
	@Test
	void testFeeController() {
		assert feeController.setFee("city", "tallinn", "car", 1.0).getBody();
		assert feeController.getFee("tallinn", "car", Long.MAX_VALUE).getBody() == 1.0;
		feeController.setFee("city", "tallinn", "car", 4.0).getBody();
	}

}
