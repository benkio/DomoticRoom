package domainModel;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import interfaces.IConvertor;
import model.convertors.GasConverter;

public class GasConvertorTestPlan {

	private IConvertor<Boolean> gc = new GasConverter();
	
	@Test
	public void convertValueIdentityTest() {
		List<String> goodValues = Arrays.asList("0","1");
		List<String> badValues = Arrays.asList("-2","95", "59", "-32", "bello");
		
		goodValues.forEach(s -> {
			boolean expected;
			if (s.equals("0")) expected = true; else expected = false;
		Assert.assertEquals(gc.convertValue(s), expected);
		});
		badValues.forEach(s ->{ 
			try{
				gc.convertValue(s);
				Assert.fail();
			}catch(Exception e){}
		});	
	}
	}

}
