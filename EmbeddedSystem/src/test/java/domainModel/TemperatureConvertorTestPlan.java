package domainModel;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import interfaces.IConvertor;
import model.convertors.TemperatureConverter;

public class TemperatureConvertorTestPlan {

	private IConvertor<Double> tc = new TemperatureConverter();
	
	@Test
	public void convertValueIdentityTest() {
		List<String> goodValues = Arrays.asList("22","40", "9", "5", "13");
		List<String> badValues = Arrays.asList("-2","95", "59", "-32", "bello");
		
		goodValues.forEach(s -> Assert.assertEquals((double) tc.convertValue(s), Double.parseDouble(s)));
		badValues.forEach(s ->{ 
			try{
				tc.convertValue(s);
				Assert.fail();
			}catch(Exception e){}
		});	
	}

}
