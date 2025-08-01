package smn.practice.practice.smn.library.utils;

public class StackTraceUtil {

    public static StackTraceElement[] getCroppedRealStackTrack(StackTraceElement[] stackTrace, String ignorePackage, int maxDepth) {
        return cropStackTrace(getRealStackTrack(stackTrace, ignorePackage), maxDepth);
    }

    /**
     * 裁剪堆栈信息
     *
     * @param callStack
     * @param maxDepth
     * @return
     */
    private static StackTraceElement[] cropStackTrace(StackTraceElement[] callStack, int maxDepth) {
        int realDepth = callStack.length;
        if (realDepth > 0) {
            realDepth = Math.min(maxDepth, realDepth);
        }

        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        System.arraycopy(callStack, 0, realStack, 0, realDepth);

        return realStack;
    }


    /**
     * 获取除了忽略包名之外的堆栈信息
     *
     * @param stackTrace
     * @param ignorePackage
     * @return
     */
     private static StackTraceElement[] getRealStackTrack(StackTraceElement[] stackTrace, String ignorePackage) {
        int ignoreDepth = 0;
        int allDepth = stackTrace.length;
        String className;
         for (int i = allDepth - 1; i >= 0; i--) {
             className = stackTrace[i].getClassName();
             if (ignorePackage != null && className.startsWith(ignorePackage)) {
                 ignoreDepth = i + 1;
                 break;
             }
         }

         int realDepth = allDepth - ignoreDepth;
         StackTraceElement[] realStack = new StackTraceElement[realDepth];
         System.arraycopy(stackTrace, ignoreDepth, realStack, 0, realDepth);

         return realStack;
     }

}
