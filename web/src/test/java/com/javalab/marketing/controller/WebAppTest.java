package com.javalab.marketing.controller;

import com.javalab.marketing.dao.OrderManager;
import com.javalab.marketing.dao.SupportManager;
import com.javalab.marketing.dao.UserManager;
import com.javalab.marketing.models.Order;
import com.javalab.marketing.models.Support;
import com.javalab.marketing.models.User;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.sql.SQLException;
import java.util.List;

public class WebAppTest {

    static WebDriver driver;

    @BeforeClass
    public static void openBrowser(){
        driver = new FirefoxDriver();
    }

    @Test
    public void testOpenMain(){
        driver.get("http://localhost:8080/");
        Assert.assertEquals("Marketing Group",driver.getTitle());
    }

    @Test
    public void testRegistration() throws SQLException, ClassNotFoundException {
        driver.get("http://localhost:8080/registration");
        WebElement email=driver.findElement(By.id("email"));
        email.sendKeys("test@test.ru");
        WebElement username=driver.findElement(By.id("username"));
        username.sendKeys("test");
        WebElement password=driver.findElement(By.id("password"));
        password.sendKeys("test");
        WebElement submit=driver.findElement(By.id("submit"));
        submit.click();
        driver.get("http://localhost:8080/signin");
        WebElement jusername=driver.findElement(By.id("j_username"));
        jusername.sendKeys("test");
        WebElement jpassword=driver.findElement(By.id("j_password"));
        jpassword.sendKeys("test");
        WebElement login=driver.findElement(By.id("submit"));
        login.click();
        WebElement check = driver.findElement(By.className("text-muted"));
        Assert.assertEquals("test",check.getText());
        UserManager userManager = new UserManager();
        userManager.deleteUser("test");
    }

    @Test
    public void testCreateOrder() throws SQLException, ClassNotFoundException {
        driver.get("http://localhost:8080/signin");
        UserManager userManager = new UserManager();
        User user = new User("test","test","test","ROLE_USER");
        user.setId(9999);
        userManager.createWithId(user);
        driver.get("http://localhost:8080/signin");
        WebElement jusername=driver.findElement(By.id("j_username"));
        jusername.sendKeys("test");
        WebElement jpassword=driver.findElement(By.id("j_password"));
        jpassword.sendKeys("test");
        WebElement login=driver.findElement(By.id("submit"));
        login.click();
        driver.get("http://localhost:8080/secure/orders/add");
        WebElement appname = driver.findElement(By.id("appname"));
        appname.sendKeys("test");
        WebElement hrefappstore = driver.findElement(By.id("hrefappstore"));
        hrefappstore.sendKeys("test");
        WebElement hrefplaymarket = driver.findElement(By.id("hrefplaymarket"));
        hrefplaymarket.sendKeys("test");
        WebElement service = driver.findElement(By.id("clicks"));
        service.click();
        WebElement count = driver.findElement(By.id("count"));
        count.sendKeys("100");
        WebElement submit = driver.findElement(By.id("submit"));
        submit.click();
        driver.get("http://localhost:8080/secure/orders");
        WebElement order = driver.findElement(By.id("test"));
        Assert.assertEquals("test",order.getText());
        OrderManager orderManager = new OrderManager();
        List<Order> orders=orderManager.getAll();
        for(Order o:orders) {
            if(o.getAppname().equals("test")){
                orderManager.deleteOrder(o.getId());
            }
        }
        userManager.deleteUser("test");
    }

    @Test
    public void testAcceptOrder() throws SQLException, ClassNotFoundException {
        Order order2 = new Order(9999,"test","test","test","clicks",1000,9999,"В обработке");
        OrderManager orderManager = new OrderManager();
        orderManager.createOrder(order2);
        driver.get("http://localhost:8080/signin");
        WebElement jusername=driver.findElement(By.id("j_username"));
        jusername.sendKeys("roman");
        WebElement jpassword=driver.findElement(By.id("j_password"));
        jpassword.sendKeys("111");
        WebElement login=driver.findElement(By.id("submit"));
        login.click();
        driver.get("http://localhost:8080/admin/orders");
        WebElement order = driver.findElement(By.id("test"));
        order.click();
        WebElement submit = driver.findElement(By.id("submit"));
        submit.click();
        List<Order> orders = orderManager.getAll();
        for(Order o:orders) {
            if(o.getAppname().equals("test")){
                orderManager.deleteOrder(o.getId());
            }
        }
    }

    @Test
    public  void testPayment() throws SQLException, ClassNotFoundException {
        int id = 0;
        UserManager userManager = new UserManager();
        User user = new User("test","test","test","ROLE_USER");
        List<User> users = userManager.getAll();
        for(User u:users){
            if(u.getUsername().equals("test")){
                id=u.getId();
            }
        }
        Order order = new Order(id,"test","test","test","clicks",100,100,"Требуется оплата");
        OrderManager orderManager = new OrderManager();
        orderManager.createOrder(order);
        driver.get("http://localhost:8080/signin");
        WebElement jusername=driver.findElement(By.id("j_username"));
        jusername.sendKeys("test");
        WebElement jpassword=driver.findElement(By.id("j_password"));
        jpassword.sendKeys("test");
        WebElement login=driver.findElement(By.id("submit"));
        login.click();
        driver.get("http://localhost:8080/secure/payment");
        WebElement testorder = driver.findElement(By.name("testpay"));
        testorder.click();
        driver.get("http://localhost:8080/secure/monitoring");
        testorder = driver.findElement(By.id("test"));
        Assert.assertNotNull(testorder);
        userManager.deleteUser("test");
        List<Order> orders=orderManager.getAll();
        for(Order o:orders) {
            if(o.getAppname().equals("test")){
                orderManager.deleteOrder(o.getId());
            }
        }
    }

    @Test
    public void testWriteSuppot() throws SQLException, ClassNotFoundException {
        UserManager userManager = new UserManager();
        User user = new User("test","test","test","ROLE_USER");
        driver.get("http://localhost:8080/signin");
        WebElement jusername=driver.findElement(By.id("j_username"));
        jusername.sendKeys("test");
        WebElement jpassword=driver.findElement(By.id("j_password"));
        jpassword.sendKeys("test");
        WebElement login=driver.findElement(By.id("submit"));
        login.click();
        driver.get("http://localhost:8080/secure/support/add");
        WebElement theme = driver.findElement(By.id("theme"));
        WebElement messageuser = driver.findElement(By.id("messageuser"));
        theme.sendKeys("test");
        messageuser.sendKeys("test");
        WebElement submit = driver.findElement(By.id("submit"));
        submit.click();
        driver.get("http://localhost:8080/secure/support");
        WebElement support = driver.findElement(By.className("test"));
        Assert.assertNotNull(support);
        userManager.deleteUser("test");
        SupportManager supportManager = new SupportManager();
        List<Support> supports = supportManager.getAll();
        for(Support s:supports){
            if(s.getMessageadmin().equals("test")){
                supportManager.removeSupport(s.getId());
            }
        }
    }

    @Test
    public void testAnswerSupport() throws SQLException, ClassNotFoundException {
        SupportManager supportManager = new SupportManager();
        Support support = new Support(9999,"test","test","test",0);
        support.setId(9999);
        supportManager.createSupportWithId(support);
        driver.get("http://localhost:8080/signin");
        WebElement jusername=driver.findElement(By.id("j_username"));
        jusername.sendKeys("roman");
        WebElement jpassword=driver.findElement(By.id("j_password"));
        jpassword.sendKeys("111");
        WebElement login=driver.findElement(By.id("submit"));
        login.click();
        driver.get("http://localhost:8080/admin/supports");
        WebElement support1 = driver.findElement(By.className("test"));
        support1.click();
        WebElement messageadmin = driver.findElement(By.id("messageadmin"));
        messageadmin.sendKeys("test");
        WebElement submit = driver.findElement(By.id("submit"));
        submit.click();
        List<Support> supports = supportManager.getAllById(9999);
        supportManager.removeSupport(supports.get(0).getId());
    }
}
