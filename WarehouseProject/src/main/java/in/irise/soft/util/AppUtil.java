package in.irise.soft.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//JAVA#8 static method allowed in interface
public interface AppUtil {

	public static String getCurrentDateTime() {
		return new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss")
				.format(new Date());
	}

	public static Map<Integer, String> convertToMap(
			List<Object[]> list) 
	{

		return list.stream()
				.collect(
						Collectors.toMap(
								ob->(Integer)ob[0], 
								ob->(String)ob[1]
								)
						);
	}

	public static List<String> getUomTypes() {
		return Arrays.asList("PACKING","NO PACKING","NA");
	}


}
