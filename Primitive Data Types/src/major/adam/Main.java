package major.adam;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        //note: generally use int unless good reason not to
        //boolean = 1bit
        //byte = 8bit
        //char = 16bits (unicode char \u0000)
        //short = 16bits
        //int = 32bits
        //long = 64bits  (add 'L' at end to clarify)
        //float = 32bits
        //double = 64bits


        int myValue = 10_000;
        int minIntValue = Integer.MIN_VALUE;
        int maxIntValue = Integer.MAX_VALUE;

        System.out.println("Integer Min = " + minIntValue);
        System.out.println("Integer Max = " + maxIntValue);
        System.out.println("overflow - " + (maxIntValue + 1));
        System.out.println("underflow - " + (minIntValue - 1));

        byte minByteValue = Byte.MIN_VALUE;
        byte maxByteValue = Byte.MAX_VALUE;
        System.out.println("min byte value: " + minByteValue);
        System.out.println("max byte value: " + maxByteValue);

        short minShortValue = Short.MIN_VALUE;
        short maxShortValue = Short.MAX_VALUE;
        System.out.println("min Short value: " + minShortValue);
        System.out.println("max Short value: " + maxShortValue);

        long myLong = 10L;
        long minLongValue = Long.MIN_VALUE;
        long maxLongValue = Long.MAX_VALUE;
        System.out.println("min Long value: " + minLongValue);
        System.out.println("max Long value: " + maxLongValue);


        //casting
        //this is the compiler error given
        // byte myByte = (minByteValue / 2);

        //we know it will fit in a byte so we can cast
        byte myCastedByte = (byte) (minByteValue /2);

        //DOUBLE AND FLOAT (double is preferred and assumed type over float)
        //NOTE: use BigDecimal class when calculating precise values like in currency conversion
        BigDecimal precise = new BigDecimal(4.5);
        float declaredFloat = 4f;
        double declaredDouble = 4d;

        float minFloatValue = Float.MIN_VALUE;
        float maxFloatValue = Float.MAX_VALUE;
        System.out.println("min Float value: " + minFloatValue);
        System.out.println("max Float value: " + maxFloatValue);
        
        double minDoubleValue = Double.MIN_VALUE;
        double maxDoubleValue = Double.MAX_VALUE;
        System.out.println("min Double value: " + minDoubleValue);
        System.out.println("max Double value: " + maxDoubleValue);
        
     
        //char and boolean (unicode-table.com)
        char unicodeChar = '\u0044';

        System.out.println(unicodeChar);
        
        
        double double1 = 20.0;
        double double2 = 80.0;

        double sumMultiplied = (double1 + double2) * 100;
        double remainder = sumMultiplied % 40;
        boolean isRemainderZero = remainder == 0;
        System.out.println("remainder: " + remainder);
        System.out.println("isRemainderZero: " + isRemainderZero);
        
        
        
    }
}
