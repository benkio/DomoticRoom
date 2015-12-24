package domainModel;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import interfaces.IConvertor;
import model.convertors.HumidityConverter;

public class UmidityConvertorTestPlan {

	private IConvertor<Double> uc = new HumidityConverter();
	
	@Test
	public void convertValueIdentityTest() {
		List<String> goodValues = Arrays.asList("22","40", "30", "55", "63","71");
		List<String> badValues = Arrays.asList("-2","95", "89", "-32", "becco","10","0");
		
		goodValues.forEach(s -> Assert.assertEquals((double) uc.convertValue(s), Double.parseDouble(s)));
		badValues.forEach(s ->{ 
			try{
				uc.convertValue(s);
				Assert.fail();
			}catch(Exception e){}
		});	
	}

}
