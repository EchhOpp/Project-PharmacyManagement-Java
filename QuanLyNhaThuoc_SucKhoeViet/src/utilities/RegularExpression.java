package utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpression 
{
	public static boolean checkMatch(String input, String regex)
	{
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}
	
	public static boolean checkFind(String input, String regex)
	{
		Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}
}
