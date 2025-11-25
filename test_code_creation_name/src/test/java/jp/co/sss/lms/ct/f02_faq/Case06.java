package jp.co.sss.lms.ct.f02_faq;

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

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// 上部メニューの「機能」リンクを押下する
		WebDriverUtils.webDriver.findElement(By.className("dropdown")).click();

		// ドロップダウンメニューの表示を確認する
		WebElement list = WebDriverUtils.webDriver.findElement(By.linkText("ヘルプ"));
		assertEquals("ヘルプ", list.getText());

		// エビデンスを取得する①
		getEvidence(new Object(){}, "01");

		// ドロップダウンメニューの「ヘルプ」リンクを押下する
		WebDriverUtils.webDriver.findElement(By.linkText("ヘルプ")).click();

		// 画面遷移が正しく行われたか確認する
		WebDriverUtils.visibilityTimeout(By.tagName("h2"), 10);
		WebElement title = WebDriverUtils.webDriver.findElement(By.tagName("h2"));
		assertEquals("ヘルプ", title.getText());

		// エビデンスを取得する②
		getEvidence(new Object(){}, "02");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// 「よくある質問」リンクを押下する
		WebDriverUtils.webDriver.findElement(By.linkText("よくある質問")).click();
		WebDriverUtils.pageLoadTimeout(10);

		// 別タブに移動する
		Object[] windowHandles = WebDriverUtils.webDriver.getWindowHandles().toArray();
		WebDriverUtils.webDriver.switchTo().window((String) windowHandles[1]);

		// 画面遷移が正しく行われたか確認する
		WebDriverUtils.visibilityTimeout(By.tagName("h2"), 10);
		WebElement title = WebDriverUtils.webDriver.findElement(By.tagName("h2"));
		assertEquals("よくある質問", title.getText());

		// エビデンスを取得する
		getEvidence(new Object(){});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		// 「【研修関係】」リンクを押下する
		WebDriverUtils.webDriver.findElement(By.linkText("【研修関係】")).click();

		// 期待値通りの検索結果2件だけが表示されていることを確認する
		WebDriverUtils.visibilityTimeout(By.className("sorting_1"), 10);
		List<WebElement> questions = WebDriverUtils.webDriver.findElements(By.className("sorting_1"));
		assertEquals(2,questions.size());
		assertEquals("Q.キャンセル料・途中退校について", questions.get(0).getText());
		assertEquals("Q.研修の申し込みはどのようにすれば良いですか？", questions.get(1).getText());

		// エビデンスを取得する
		scrollTo("860");
		getEvidence(new Object(){});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		// 検索結果の質問を押下する
		List<WebElement> questions = WebDriverUtils.webDriver.findElements(By.className("sorting_1"));
		for (WebElement question : questions) {
			question.click();
		}
		
		// 質問の解答が表示されること確認する
		WebDriverUtils.visibilityTimeout(By.className("fs18"), 10);
		List<WebElement> answers = WebDriverUtils.webDriver.findElements(By.className("fs18"));
		assertEquals("A.\s受講者の退職や解雇等、やむを得ない事情による途中終了に関してなど、事情をお伺いした上で、協議という形を取らせて頂きます。\s"
				+ "弊社営業担当までご相談下さい。", answers.get(0).getText());
		assertEquals("A.\s営業担当がいる場合は、営業担当までご連絡ください。\s申し込み方法についてご案内させていただきます。\s"
				+ "なお、弊社営業営業がいない場合は、東京ITスクール運営事務局までご連絡いただけると幸いです。", answers.get(1).getText());
		
		// エビデンスを取得する
		scrollTo("860");
		getEvidence(new Object(){});
	}

}
