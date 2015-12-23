package domainModel;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import models.GasConverter;

public class GasConvertorTestPlan {

	private GasConverter gc = new GasConverter();
	
	@Test
	public void convertValueIdentityTest() {
		List<String> goodValues = Arrays.asList("0","1");
		List<String> badValues = Arrays.asList("-2","95", "59", "-32", "bello");
		
		goodValues.forEach(s -> Assert.assertEquals(gc.convertValue(s), Double.parseDouble(s)));
		badValues.forEach(s ->{ 
			try{
				gc.convertValue(s);
				Assert.fail();
			}catch(Exception e){}
		});	
	}

}
