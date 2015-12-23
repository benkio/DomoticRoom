package domainModel;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import models.PirConverter;

public class PirConvertorTestPlan {

	private PirConverter pc = new PirConverter();
	
	@Test
	public void convertValueIdentityTest() {
		List<String> goodValues = Arrays.asList("0","1");
		List<String> badValues = Arrays.asList("-2","95", "59", "-32", "bello");
		
		goodValues.forEach(s -> Assert.assertEquals(pc.convertValue(s), Double.parseDouble(s)));
		badValues.forEach(s ->{ 
			try{
				pc.convertValue(s);
				Assert.fail();
			}catch(Exception e){}
		});	
	}

}
