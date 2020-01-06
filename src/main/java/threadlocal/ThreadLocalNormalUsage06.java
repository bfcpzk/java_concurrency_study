package threadlocal;

/**
 * 演示ThreadLocal用法2：避免参数传递的麻烦。
 */
public class ThreadLocalNormalUsage06 {
    public static void main(String[] args) throws InterruptedException {
        new Service1().process();
    }
}

class Service1 {
    public void process() {
        User user = new User("Mike");
        UserContextHolder.holder.set(user);
        new Service2().process();
    }
}

class Service2 {
    public void process() {
        User user = UserContextHolder.holder.get();
        System.out.println("Service2拿到对象：" + user.name);
        UserContextHolder.holder.remove();
        User user1 = new User("Peter");
        UserContextHolder.holder.set(user1);
        new Service3().process();
    }
}

class Service3 {
    public void process() {
        User user = UserContextHolder.holder.get();
        System.out.println("Service3拿到对象：" +user.name);
    }
}

class UserContextHolder {
    public static ThreadLocal<User> holder = new ThreadLocal<>();
}

class User {
    String name;
    public User(String name) {
        this.name = name;
    }
}
