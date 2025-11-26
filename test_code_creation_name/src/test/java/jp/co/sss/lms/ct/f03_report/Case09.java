package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {
		// 「ようこそ受講生ＡＡ１さん」リンクを押下する
		WebDriverUtils.webDriver.findElement(By.linkText("ようこそ受講生ＡＡ１さん")).click();
		
		// 画面遷移が正しく行われたか確認する
		WebDriverUtils.visibilityTimeout(By.tagName("h2"), 10);
		WebElement title = WebDriverUtils.webDriver.findElement(By.tagName("h2"));
		assertEquals("ユーザー詳細", title.getText());
		
		// エビデンスを取得する
		getEvidence(new Object(){});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// 該当レポートの「詳細」ボタンを押下する
		scrollTo("860");
		WebDriverUtils.webDriver.findElement(By.xpath("//*[@id='main']/table[3]/tbody/tr[3]/td[5]/form[2]/input[1]")).click();

		// 画面遷移が正しく行われたか確認する
		WebDriverUtils.visibilityTimeout(By.tagName("h2"), 10);
		WebElement title = WebDriverUtils.webDriver.findElement(By.tagName("h2"));
		assertEquals("週報【デモ】\s2022年10月2日", title.getText()); 
		
		// エビデンスを取得する
		getEvidence(new Object(){});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {
		// 「学習項目」を未入力にする
		WebDriverUtils.visibilityTimeout(By.id("intFieldName_0"), 5);
		WebElement learningItem = WebDriverUtils.webDriver.findElement(By.id("intFieldName_0"));
		learningItem.clear();
		
		// エビデンスを取得する①
		getEvidence(new Object(){}, "01");
		
		// 「提出する」ボタンを押下する
		scrollTo("400");
		WebDriverUtils.webDriver.findElement(By.className("btn-primary")).click();
		
		// エラーメッセージが表示されていることを確認する
		WebDriverUtils.visibilityTimeout(By.className("error"), 5);
		WebElement error = WebDriverUtils.webDriver.findElement(By.className("error"));
		assertEquals("*\s理解度を入力した場合は、学習項目は必須です。", error.getText());
		
		// エビデンスを取得する②
		getEvidence(new Object(){}, "02");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {
		// 「学習項目」を入力する
		WebDriverUtils.visibilityTimeout(By.id("intFieldName_0"), 5);
		WebElement learningItem = WebDriverUtils.webDriver.findElement(By.id("intFieldName_0"));
		learningItem.clear();
		learningItem.sendKeys("ITリテラシー①");
		
		// 「理解度」を入力する
		WebDriverUtils.visibilityTimeout(By.id("intFieldValue_0"), 5);
		Select understanding = new Select(WebDriverUtils.webDriver.findElement(By.id("intFieldValue_0")));
		understanding.selectByValue("");
		
		// エビデンスを取得する①
		getEvidence(new Object(){}, "01");
		
		// 「提出する」ボタンを押下する
		scrollTo("400");
		WebDriverUtils.webDriver.findElement(By.className("btn-primary")).click();
		
		// エラーメッセージが表示されていることを確認する
		WebDriverUtils.visibilityTimeout(By.className("error"), 5);
		WebElement error = WebDriverUtils.webDriver.findElement(By.className("error"));
		assertEquals("*\s学習項目を入力した場合は、理解度は必須です。", error.getText());
		
		// エビデンスを取得する②
		getEvidence(new Object(){}, "02");
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {
		// 「理解度」を入力する
		WebDriverUtils.visibilityTimeout(By.id("intFieldValue_0"), 5);
		Select understanding = new Select(WebDriverUtils.webDriver.findElement(By.id("intFieldValue_0")));
		understanding.selectByValue("2");
		
		// 「目標の達成度」を入力する
		WebDriverUtils.visibilityTimeout(By.id("content_0"), 5);
		WebElement achievement = WebDriverUtils.webDriver.findElement(By.id("content_0"));
		achievement.clear();
		achievement.sendKeys("A");
		
		// エビデンスを取得する①
		getEvidence(new Object(){}, "01");
		
		// 「提出する」ボタンを押下する
		scrollTo("400");
		WebDriverUtils.webDriver.findElement(By.className("btn-primary")).click();
		
		// エラーメッセージが表示されていることを確認する
		WebDriverUtils.visibilityTimeout(By.className("error"), 5);
		WebElement error = WebDriverUtils.webDriver.findElement(By.className("error"));
		assertEquals("*\s目標の達成度は半角数字で入力してください。", error.getText());
		
		// エビデンスを取得する②
		getEvidence(new Object(){}, "02");
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {
		// 「目標の達成度」を入力する
		WebDriverUtils.visibilityTimeout(By.id("content_0"), 5);
		WebElement achievement = WebDriverUtils.webDriver.findElement(By.id("content_0"));
		achievement.clear();
		achievement.sendKeys("11");
		
		// エビデンスを取得する①
		getEvidence(new Object(){}, "01");
		
		// 「提出する」ボタンを押下する
		scrollTo("400");
		WebDriverUtils.webDriver.findElement(By.className("btn-primary")).click();
		
		// エラーメッセージが表示されていることを確認する
		WebDriverUtils.visibilityTimeout(By.className("error"), 5);
		WebElement error = WebDriverUtils.webDriver.findElement(By.className("error"));
		assertEquals("*\s目標の達成度は、半角数字で、1～10の範囲内で入力してください。", error.getText());
		
		// エビデンスを取得する②
		getEvidence(new Object(){}, "02");
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {
		// 「目標の達成度」を入力する
		WebDriverUtils.visibilityTimeout(By.id("content_0"), 5);
		WebElement achievement = WebDriverUtils.webDriver.findElement(By.id("content_0"));
		achievement.clear();
		
		// 「所感」を入力する
		WebDriverUtils.visibilityTimeout(By.id("content_1"), 5);
		WebElement impression = WebDriverUtils.webDriver.findElement(By.id("content_1"));
		impression.clear();
		
		// エビデンスを取得する①
		scrollTo("250");
		getEvidence(new Object(){}, "01");
		
		// 「提出する」ボタンを押下する
		scrollTo("400");
		WebDriverUtils.webDriver.findElement(By.className("btn-primary")).click();
		
		// エラーメッセージが表示されていることを確認する
		WebDriverUtils.visibilityTimeout(By.className("error"), 5);
		List<WebElement> errors = WebDriverUtils.webDriver.findElements(By.className("error"));
		assertEquals("*\s目標の達成度は半角数字で入力してください。", errors.get(0).getText());
		assertEquals("*\s所感は必須です。", errors.get(1).getText());
		
		// エビデンスを取得する②
		scrollTo("250");
		getEvidence(new Object(){}, "02");
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {
		// 「目標の達成度」を入力する
		WebDriverUtils.visibilityTimeout(By.id("content_0"), 5);
		WebElement achievement = WebDriverUtils.webDriver.findElement(By.id("content_0"));
		achievement.clear();
		achievement.sendKeys("5");
		
		// 「所感」「一週間の振り返り」入力用の2050字の文字列を用意する
		String base = "あ";
		String repeatBase = base.repeat(2050);
		
		// 「所感」を入力する
		WebDriverUtils.visibilityTimeout(By.id("content_1"), 5);
		WebElement impression = WebDriverUtils.webDriver.findElement(By.id("content_1"));
		impression.clear();
		impression.sendKeys(repeatBase);
		
		// 「一週間の振り返り」を入力する
		WebDriverUtils.visibilityTimeout(By.id("content_2"), 5);
		WebElement weeklyReview = WebDriverUtils.webDriver.findElement(By.id("content_2"));
		weeklyReview.clear();
		weeklyReview.sendKeys(repeatBase);
		
		// エビデンスを取得する①
		scrollTo("250");
		getEvidence(new Object(){}, "01");
		
		// 「提出する」ボタンを押下する
		scrollTo("400");
		WebDriverUtils.webDriver.findElement(By.className("btn-primary")).click();
		
		// エラーメッセージが表示されていることを確認する
		WebDriverUtils.visibilityTimeout(By.className("error"), 5);
		List<WebElement> errors = WebDriverUtils.webDriver.findElements(By.className("error"));
		assertEquals("*\s所感の長さが最大値(2000)を超えています。", errors.get(0).getText());
		assertEquals("*\s一週間の振り返りの長さが最大値(2000)を超えています。", errors.get(1).getText());
		
		// エビデンスを取得する②
		scrollTo("250");
		getEvidence(new Object(){}, "02");
	}

}
