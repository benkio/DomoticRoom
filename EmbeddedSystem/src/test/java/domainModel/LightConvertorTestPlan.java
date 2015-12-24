package domainModel;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import interfaces.IConvertor;
import model.convertors.LightConverter;

public class LightConvertorTestPlan {

	private IConvertor<Boolean> lc = new LightConverter();
	
	@Test
	public void convertValueIdentityTest() {
		List<String> goodValues = Arrays.asList("0","1");
		List<String> badValues = Arrays.asList("-2","95", "59", "-32", "bello");
		
		goodValues.forEach(s -> {
			boolean expected;
			if (s.equals("0")) expected = true; else expected = false;
		Assert.assertEquals(lc.convertValue(s), expected);
		});
		badValues.forEach(s ->{ 
			try{
				lc.convertValue(s);
				Assert.fail();
			}catch(Exception e){}
		});	
	}
	}

}