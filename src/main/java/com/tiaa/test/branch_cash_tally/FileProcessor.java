package com.tiaa.test.branch_cash_tally;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiaa.test.branch_cash_tally.gen.CmFoodChainType;
import com.tiaa.test.branch_cash_tally.gen.OrderDetailType;
import com.tiaa.test.branch_cash_tally.gen.OrdersType;
import com.tiaa.test.branch_cash_tally.json.Branch;
import com.tiaa.test.branch_cash_tally.json.CmFoodChain;
import com.tiaa.test.branch_cash_tally.json.OrderDetails;
import com.tiaa.test.branch_cash_tally.json.Orders;

/**
 *
 * This class will process the input files and creates the outp file
 *
 * @author PatilSan
 *
 */
@Component
public class FileProcessor {

	private Logger logger = LoggerFactory.getLogger(FileProcessor.class);

	@Autowired
	private Unmarshaller jaxbUnMarshaller;

	/**
	 * This method reads files from input directory and process these files one
	 * by one to create the output files
	 */
	public void processInputFiles() {

		ClassLoader classLoader = getClass().getClassLoader();
		File inputDirectory = new File(classLoader.getResource("input").getFile());

		List<Branch> cashTallyOk = new ArrayList<>();
		List<Branch> cashTallyNotOk = new ArrayList<>();

		File[] listOfInputFiles = inputDirectory.listFiles();
		for (File file : listOfInputFiles) {

			String fileName = file.getName();
			String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
			if (extension.equals("xml")) {
				processXmlFile(file, cashTallyNotOk, cashTallyOk);
			} else {
				processJsonFile(file, cashTallyNotOk, cashTallyOk);

			}

		}

		writeOutputFile(cashTallyOk, cashTallyNotOk);

	}

	/**
	 * This method writes the output file
	 *
	 * @param cashTallyNotOk
	 * @param cashTallyOk
	 */
	protected void writeOutputFile(List<Branch> cashTallyOk, List<Branch> cashTallyNotOk) {
		// TODO Auto-generated method stub
		ObjectMapper mapper = new ObjectMapper();

		try {

			File matchFile = new File("D:/output/match.json");
			matchFile.createNewFile();

			File misMatchFile = new File("D:/output/notmatched.json");
			misMatchFile.createNewFile();
			mapper.writeValue(matchFile, cashTallyOk);
			mapper.writeValue(misMatchFile, cashTallyNotOk);
		} catch (IOException e) {
			logger.error("IOException occured while reading JSON input file", e);
		}

	}

	/**
	 * This method processes JSON file and checks if the sum of orders and total
	 * collection of branch matches or not
	 *
	 * @param file
	 * @param cashTallyNotOk
	 * @param cashTallyOk
	 */
	protected void processJsonFile(File file, List<Branch> cashTallyNotOk, List<Branch> cashTallyOk) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		Branch branch = new Branch();
		try {
			CmFoodChain cmFoodChainTypeJson = mapper.readValue(file, CmFoodChain.class);

			System.out.println(cmFoodChainTypeJson.getBranch().getLocationid());
			Orders orders = cmFoodChainTypeJson.getOrders();
			List<OrderDetails> orderDetails = orders.getOrderdetail();
			Double sumOfOrders = orderDetails.stream().mapToDouble(order -> order.getBillamount()).sum();

			branch = cmFoodChainTypeJson.getBranch();
			branch.setSumoforder(sumOfOrders.floatValue());

			if (sumOfOrders.floatValue() == cmFoodChainTypeJson.getBranch().getTotalcollection()) {
				cashTallyOk.add(branch);
			} else {
				cashTallyNotOk.add(branch);
			}

		} catch (JsonParseException e) {
			logger.error("JsonParseException occured while reading JSON input file", e);
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException occured while reading JSON input file", e);
		} catch (IOException e) {
			logger.error("IOException occured while reading JSON input file", e);
		}
	}

	/**
	 * This method processes the XML file and checks if sum of orders of branch
	 * matches the total collection of branch
	 *
	 * @param file
	 * @param cashTallyNotOk
	 * @param cashTallyOk
	 * @throws JAXBException
	 */
	protected void processXmlFile(File file, List<Branch> cashTallyNotOk, List<Branch> cashTallyOk) {
		CmFoodChainType cmFoodChainType = null;
		try {
			cmFoodChainType = (CmFoodChainType) jaxbUnMarshaller.unmarshal(file);
		} catch (JAXBException e) {
			logger.error("JAXBException is thrown while reading XML input ", e);
			System.exit(1);

		}

		Branch branch = new Branch();

		OrdersType orders = cmFoodChainType.getOrders();
		List<OrderDetailType> orderDetails = orders.getOrderdetail();

		Double sumOfOrders = orderDetails.stream().mapToDouble(order -> order.getBillamount()).sum();

		double totalCollection = Double.valueOf(cmFoodChainType.getBranch().getTotalCollection());

		branch.setLocation(cmFoodChainType.getBranch().getLocation());
		branch.setLocationid(cmFoodChainType.getBranch().getLocationId());
		branch.setTotalcollection(Float.valueOf(cmFoodChainType.getBranch().getTotalCollection()));
		branch.setSumoforder(sumOfOrders.floatValue());

		if (sumOfOrders.floatValue() == totalCollection) {
			cashTallyOk.add(branch);
		} else {
			cashTallyNotOk.add(branch);
		}
	}

}
