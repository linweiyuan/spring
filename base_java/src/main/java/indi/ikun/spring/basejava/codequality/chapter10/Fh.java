package indi.ikun.spring.basejava.codequality.chapter10;

/**
 * 137.调整JVM参数以提升性能
 *
 * 每一段Java程序都要在JVM中运行，
 * 如果程序已经优化到了极致，但还是觉得性能比较低，
 * 那JVM的优化就要提到日程上来了。
 * 不过，由于JVM又是系统运行的容器，
 * 所以稳定性也是必须考虑的，
 * 过度的优化可能就会导致系统故障频繁发生，致使系统质量大幅下降。
 * 下面提供了四个常用的JVM优化手段
 * 1)调整堆内存大小
 *      在JVM中有两种内存：栈内存（Stack）和堆内存（Heap），
 *      栈内存的特点是空间比较小，速度快，用来存放对象的引用及程序中的基本类型；
 *      堆内存的特点是空间比较大，速度慢，一般对象都会在这里生成、使用和消亡
 *      1）栈空间是由线程开辟，线程结束，栈空间由JVM回收，
 *          因此它的大小一般不会对性能有太大的影响，
 *          但是它会影响系统的稳定性，
 *          在超过栈内存的容量时，系统会报StackOverflowError错误。
 *          可以通过“java-Xss <size>”设置栈内存大小来解决此类问题
 *      2）堆内存的调整不能太随意，
 *          调整得太小，会导致Full GC频繁执行，轻则导致系统性能急速下降，重则导致系统根本无法使用；
 *          调整得太大，
 *              一则是浪费资源（当然，若设置了最小堆内存则可以避免此问题），
 *              二则是产生系统不稳定的情况，
 *                  例如在32位的机器上设置超过1.8GB的内存就有可能产生莫名其妙的错误。
 *                  设置初始化堆内存为1GB（也就是最小堆内存），最大堆内存为1.5GB可以用如下的参数：
 *                  java -Xmx1536m -Xms1024m
 * 2)调整堆内存中各分区的比例
 *      JVM的堆内存包括三部分：
 *          1）新生区（Young Generation Space）
 *            其中新生成的对象都在新生区，它又分为
 *               伊甸区（Eden Space）
 *               幸存0区（Survivor 0 Space）
 *               幸存1区（Survivor 1 Space）
 *          2）养老区（Tenure generation space）
 *          3）永久存储区（Permanent Space）
 *          当在程序中使用了new关键字时，首先在伊甸区生成该对象，
 *          如果伊甸区满了，则用垃圾回收器先进行回收，
 *          然后把剩余的对象移动到幸存区（0区或1区），
 *          可如果幸存区也满了呢？垃圾回收器会再回收一次，
 *          然后再把剩余的对象移动到养老区，
 *          要是养老区也满了呢？此时就会触发Full GC
 *          （这是一个非常危险的动作，JVM会停止所有的执行，所有系统资源都会让位给垃圾回收器），
 *          会对所有的对象过滤一遍，检查是否有可以回收的对象，
 *          如果还是没有的话，就抛出OutOfMemoryError错误，系统不干了
 *      如何提升性能
 *          若扩大新生区，势必会减少养老区，这就可能产生不稳定的情况，
 *          一般情况下，新生区和养老区的比例为1:3左右，设置命令如下：
 *          java -XX:NewSize=32m -XX:MaxNewSize=640m -XX:MaxPermSize=1280m -xx:NewRatio=5
 *          (新生区初始化32MB，也就是新生区最小内存为32M，最大不超过640MB，养老区最大不超过1280MB，新生区和养老区的比例为1:5)
 * 3）变更GC的垃圾回收策略
 *      Java程序性能的最大障碍就是垃圾回收，
 *      我们不知道它何时会发生，也不知道它会执行多长时间，
 *      但是我们可以想办法改变它对系统的影响，
 *      比如启用并行垃圾回收、规定并行回收的线程数量等，命令格式如下：
 *      java -XX:+UseParallelGC -XX:ParallelGCThreads=20
 *      (启用了并行垃圾收集机制，并且定义了20个收集线程（默认的收集线程等于CPU的数量），这对多CPU的系统是非常有帮助的，可以大大减少垃圾回收对系统的影响，提高系统性能)
 *      垃圾回收的策略还有很多属性可以修改，
 *          比如UseSerialGC（启用串行GC，默认值）、
 *          ScavengeBeforeFullGC（新生代GC优先于Full GC执行）、
 *          UseConcMarkSweepGC（对老生代采用并发标记交换算法进行GC）等，
 *      这些参数需要在系统中逐步调试
 * 4）更换JVM
 *      如果所有的JVM优化都不见效，那只有使用最后一招了：更换JVM，
 *      目前市面上比较流行的JVM有三个产品：
 *      Java HotSpot VM
 *          HotSpot是我们经常使用的，稳定性、可靠性都不错；
 *      Oracle JRockitJVM
 *          JRockit则以效率著称，性能是它的优势，
 *          但在决定使用该JVM之前一定要做好全面的系统测试，它的某些行为可能会在JRockit上产生Bug；
 *      IBM JVM
 *          IBMJVM也比较稳定，而且它在AIX系统上的表现要远远好于其他操作系统
 */
public class Fh {}