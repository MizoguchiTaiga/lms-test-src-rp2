package jp.co.sss.lms.ct.f02_faq;

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
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		// 検索キーワードを入力する
		WebDriverUtils.webDriver.findElement(By.name("keyword")).sendKeys("助成金");
		
		// エビデンスを取得する①
		getEvidence(new Object(){}, "01");
		
		// 「検索」ボタンを押下する
		WebDriverUtils.webDriver.findElement(By.xpath("//input[@value='検索']")).click();
		
		// 期待値通りの検索結果が表示されていることを確認する
		WebElement result = WebDriverUtils.webDriver.findElement(By.className("sorting_1"));
		assertEquals("Q.助成金書類の作成方法が分かりません", result.getText());
		
		// エビデンスを取得する②
		scrollTo("860");
		getEvidence(new Object(){}, "02");
	}
	
	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		// test05で入力したキーワードが表示されていることを確認する
		WebElement keyword = WebDriverUtils.webDriver.findElement(By.name("keyword"));
		assertEquals("助成金", keyword.getAttribute("value"));
		
		// エビデンスを取得する①
		getEvidence(new Object(){}, "01");
		
		// 「クリア」ボタンを押下する
		WebDriverUtils.webDriver.findElement(By.xpath("//input[@value='クリア']")).click();
		
		// キーワードが消去されていることを確認する
		WebElement result = WebDriverUtils.webDriver.findElement(By.name("keyword"));
		assertEquals("", result.getAttribute("value"));
		
		// エビデンスを取得する
		getEvidence(new Object(){}, "02");
	}

}
