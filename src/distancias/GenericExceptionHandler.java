package distancias;

public class GenericExceptionHandler {


	public static void printException (Exception e){
		
		String message = "\n\n**** **** **** **** **** **** **** **** *** EXCEPTION *** **** **** **** **** **** **** **** ****\n";

		
		if (e.getMessage() != null && !e.getMessage().equals(""))
			message += (  "EXCEPTION MESSAGE: " + e.getMessage() + "\n");
		
		if (e!=null)
			message += (  "EXCEPTION PRINT: " + e + "\n");
		
		StackTraceElement[] ste = e.getStackTrace();

		if (ste != null && ste.length != 0){
			message += (  "STACKTRACE:\n" );
			for (StackTraceElement elem : ste)
				message += "\t| " + elem.getClassName() + ":" + elem.getLineNumber() + "\n";
		}

		message += (  "\n**** **** **** **** **** **** **** **** *** ** END ** *** **** **** **** **** **** **** **** ****\n" );
		
		System.err.println(message);
	}
}