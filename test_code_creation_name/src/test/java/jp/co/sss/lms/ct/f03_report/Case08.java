package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// ログイン画面に遷移する
		goTo("http://localhost:8080/lms");

		// 画面遷移が正しく行われたか確認する
		WebElement title = WebDriverUtils.webDriver.findElement(By.tagName("h2"));
		assertEquals("ログイン", title.getText());

		// エビデンスを取得する
		getEvidence(new Object(){});

	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// ログインIDとパスワードを入力する
		WebDriverUtils.webDriver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		WebDriverUtils.webDriver.findElement(By.id("password")).sendKeys("TestAA01");

		// エビデンスを取得する①
		getEvidence(new Object(){}, "01");

		// ログインボタンを押下する
		WebDriverUtils.webDriver.findElement(By.className("btn-primary")).click();

		//ログインに成功し、画面遷移が正しく行われたか確認する
		WebDriverUtils.visibilityTimeout(By.className("active"), 10);
		WebElement title = WebDriverUtils.webDriver.findElement(By.className("active"));
		assertEquals("コース詳細", title.getText());

		// エビデンスを取得する②
		getEvidence(new Object(){}, "02");
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// 提出済の研修日の「詳細」ボタンを押下する
		WebDriverUtils.webDriver.findElement(By.xpath("//*[@id='main']/div/div[2]/div[2]/table/tbody/tr[2]/td[5]/form/input[3]")).click();
		
		// 画面遷移が正しく行われたか確認する
		WebDriverUtils.visibilityTimeout(By.tagName("h3"), 10);
		WebElement title = WebDriverUtils.webDriver.findElement(By.tagName("h3"));
		assertEquals("本日の内容", title.getText());
				
		// エビデンスを取得する
		getEvidence(new Object(){});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// 「提出済み週報【デモ】を確認する」ボタンを押下する
		scrollTo("400");
		WebDriverUtils.webDriver.findElement(By.xpath("//input[@value='提出済み週報【デモ】を確認する']")).click();
		
		// 画面遷移が正しく行われたか確認する
		WebDriverUtils.visibilityTimeout(By.tagName("h2"), 10);
		WebElement title = WebDriverUtils.webDriver.findElement(By.tagName("h2"));
		assertEquals("週報【デモ】\s2022年10月2日", title.getText());
		
		// エビデンスを取得する①
		getEvidence(new Object(){}, "01");
		
		// エビデンスを取得する②(※修正前の入力内容の確認用)
		scrollTo("400");
		getEvidence(new Object(){}, "02");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		// 報告内容を編集する
		WebElement report0 = WebDriverUtils.webDriver.findElement(By.id("content_0"));
		report0.clear();
		report0.sendKeys("7");
		
		WebElement report1 = WebDriverUtils.webDriver.findElement(By.id("content_1"));
		report1.clear();
		report1.sendKeys("よくできた。");
		
		WebElement report2 = WebDriverUtils.webDriver.findElement(By.id("content_2"));
		report2.clear();
		report2.sendKeys("今週はよくできた。");
		
		// エビデンスを取得する①
		getEvidence(new Object(){}, "01");
		
		// 「提出する」ボタンを押下する
		WebDriverUtils.webDriver.findElement(By.className("btn-primary")).click();
		
		// 画面遷移が正しく行われたか確認する
		WebDriverUtils.visibilityTimeout(By.tagName("h3"), 10);
		WebElement title = WebDriverUtils.webDriver.findElement(By.tagName("h3"));
		assertEquals("本日の内容", title.getText());
		
		// エビデンスを取得する②
		getEvidence(new Object(){}, "02");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		// 「ようこそ受講生ＡＡ１さん」リンクを押下する
		WebDriverUtils.webDriver.findElement(By.linkText("ようこそ受講生ＡＡ１さん")).click();;
		
		// 画面遷移が正しく行われたか確認する
		WebDriverUtils.visibilityTimeout(By.tagName("h2"), 10);
		WebElement title = WebDriverUtils.webDriver.findElement(By.tagName("h2"));
		assertEquals("ユーザー詳細", title.getText());
		
		// エビデンスを取得する
		getEvidence(new Object(){});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		// 該当レポートの「詳細」ボタンを押下する
		scrollTo("860");
		WebDriverUtils.webDriver.findElement(By.xpath("//*[@id='main']/table[3]/tbody/tr[3]/td[5]/form[1]/input[1]")).click();

		// 画面遷移が正しく行われたか確認する
		WebDriverUtils.visibilityTimeout(By.tagName("h2"), 10);
		WebElement title = WebDriverUtils.webDriver.findElement(By.tagName("h2"));
		assertEquals("週報【デモ】\s2022年10月2日", title.getText()); 
		
		// レポートの修正内容が期待値通りに更新されていることを確認する
		WebElement updatedReport0 = WebDriverUtils.webDriver.findElement(By.xpath("//*[@id='main']/div[2]/table/tbody/tr[1]/td"));
		assertEquals("7", updatedReport0.getText());
		
		WebElement updatedReport1 = WebDriverUtils.webDriver.findElement(By.xpath("//*[@id='main']/div[2]/table/tbody/tr[2]/td"));
		assertEquals("よくできた。", updatedReport1.getText());
		
		WebElement updatedReport2 = WebDriverUtils.webDriver.findElement(By.xpath("//*[@id='main']/div[2]/table/tbody/tr[3]/td"));
		assertEquals("今週はよくできた。", updatedReport2.getText());
		
		// エビデンスを取得する
		getEvidence(new Object(){});
	}

}
