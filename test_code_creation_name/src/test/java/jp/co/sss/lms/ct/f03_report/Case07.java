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
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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
		getEvidence(new Object(){}, "02");	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// 未提出の研修日の「詳細」ボタンを押下する
		WebDriverUtils.webDriver.findElement(By.xpath("//*[@id='main']/div/div[3]/div[2]/table/tbody/tr[1]/td[5]/form/input[3]")).click();
		
		// 画面遷移が正しく行われたか確認する
		WebDriverUtils.visibilityTimeout(By.tagName("h3"), 10);
		WebElement title = WebDriverUtils.webDriver.findElement(By.tagName("h3"));
		assertEquals("本日の内容", title.getText());
		
		// エビデンスを取得する
		getEvidence(new Object(){});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// 「日報【デモ】を提出する」ボタンを押下する
		WebDriverUtils.webDriver.findElement(By.xpath("//*[@id='sectionDetail']/table/tbody/tr[2]/td/form/input[5]")).click();
		
		// 画面遷移が正しく行われたか確認する
		WebDriverUtils.visibilityTimeout(By.className("control-label"), 10);
		WebElement label = WebDriverUtils.webDriver.findElement(By.className("control-label"));
		assertEquals("本日の報告内容をお書きください。", label.getText());
		
		// エビデンスを取得する
		getEvidence(new Object(){});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		// 報告内容を入力する
		WebElement report = WebDriverUtils.webDriver.findElement(By.id("content_0"));
		report.clear();
		report.sendKeys("今日はよくできた。");
		
		// エビデンスを取得する①
		getEvidence(new Object(){}, "01");

		// 「提出する」ボタンを押下する
		WebDriverUtils.webDriver.findElement(By.className("btn-primary")).click();
		
		// 画面遷移が正しく行われたか確認する
		WebDriverUtils.visibilityTimeout(By.tagName("h3"), 10);
		WebElement title = WebDriverUtils.webDriver.findElement(By.tagName("h3"));
		assertEquals("本日の内容", title.getText());
		
		// ボタン名が更新されていることを確認する
		WebDriverUtils.visibilityTimeout(By.xpath("//*[@id='sectionDetail']/table/tbody/tr[2]/td/form/input[6]"), 10);
		WebElement btn = WebDriverUtils.webDriver.findElement(By.xpath("//*[@id='sectionDetail']/table/tbody/tr[2]/td/form/input[6]"));
		assertEquals("提出済み日報【デモ】を確認する", btn.getAttribute("value"));
		
		// エビデンスを取得する②
		getEvidence(new Object(){}, "02");
	}

}
