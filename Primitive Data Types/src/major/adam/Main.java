package major.adam;

public class Main {

    public static void main(String[] args) {
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


        
    }
}
