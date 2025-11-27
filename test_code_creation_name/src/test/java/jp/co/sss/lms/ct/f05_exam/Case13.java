package jp.co.sss.lms.ct.f05_exam;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Date;
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
 * 結合テスト 試験実施機能
 * ケース13
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース13 受講生 試験の実施 結果0点")
public class Case13 {

	/** テスト07およびテスト08 試験実施日時 */
	static Date date;

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
	@DisplayName("テスト03 「試験有」の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
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
	@DisplayName("テスト04 「本日の試験」エリアの「詳細」ボタンを押下し試験開始画面に遷移")
	void test04() {
		// 「本日の試験」エリアの「詳細」ボタンを押下する
		WebDriverUtils.webDriver.findElement(By.xpath("//*[@id='sectionDetail']/table[1]/tbody/tr[2]/td[2]/form/input[1]")).click();
		
		// 画面遷移が正しく行われたか確認する
		WebDriverUtils.visibilityTimeout(By.tagName("h3"), 10);
		WebElement title = WebDriverUtils.webDriver.findElement(By.tagName("h3"));
		assertEquals("過去の試験結果", title.getText());
				
		// エビデンスを取得する①
		getEvidence(new Object(){}, "01");
		
		// エビデンスを取得する②(※試験開始前の試験結果一覧取得)
		scrollTo("400");
		getEvidence(new Object(){}, "02");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「試験を開始する」ボタンを押下し試験問題画面に遷移")
	void test05() {
		// 「試験を開始する」ボタンを押下する
		WebDriverUtils.webDriver.findElement(By.xpath("//input[@value='試験を開始する']")).click();
		
		// 画面遷移が正しく行われたか確認する
		WebDriverUtils.visibilityTimeout(By.xpath("//input[@value='確認画面へ進む']"), 10);
		WebElement btn = WebDriverUtils.webDriver.findElement(By.xpath("//input[@value='確認画面へ進む']"));
		assertEquals("確認画面へ進む", btn.getAttribute("value"));
				
		// エビデンスを取得する
		getEvidence(new Object(){});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 未回答の状態で「確認画面へ進む」ボタンを押下し試験回答確認画面に遷移")
	void test06() {
		// すべての問題が未回答であることを確認する
		// 各問題に用意されている選択肢を取得する
		for(int i = 0 ; i < 12 ; i++) {
			List<WebElement> options = WebDriverUtils.webDriver.findElements(By.name("answer[" + i + "]"));
			// 4択すべてが選択状態でないことを確認する
			for(int j = 0 ; j < 4 ; j++ ) {
				WebElement option = options.get(j);
				assertFalse(option.isSelected());
			}
		}
		
		// すべての問題が未回答の状態のエビデンスを取得する
		for (int i = 1 ; i < 13 ; i++) {
			if(i < 10) {
				getEvidence(new Object(){}, "0" + i);
			} else {
				getEvidence(new Object(){}, "" + i);
			}
			scrollBy("360");
		}
		
		// 「確認画面へ進む」ボタンを押下する
		scrollTo("5000");
		WebDriverUtils.webDriver.findElement(By.xpath("//input[@value='確認画面へ進む']")).click();
		
		// 画面遷移が正しく行われたか確認する
		WebDriverUtils.visibilityTimeout(By.id("sendButton"), 10);
		WebElement btn = WebDriverUtils.webDriver.findElement(By.id("sendButton"));
		assertEquals("回答を送信する", btn.getText());
				
		// エビデンスを取得する
		getEvidence(new Object(){}, "13");	}

	@Test
	@Order(7)
	@DisplayName("テスト07 「回答を送信する」ボタンを押下し試験結果画面に遷移")
	void test07() throws InterruptedException {
		// 回答時間を下限値以上にするための待ち処理
		Thread.sleep(1000);
		
		// 「回答を送信する」ボタンを押下する
		scrollTo("3000");
		WebDriverUtils.webDriver.findElement(By.id("sendButton")).click();;
		
		// 確認ダイアログの「OK」ボタンを押下する
		WebDriverUtils.webDriver.switchTo().alert().accept();
		
		// 画面遷移が正しく行われたか確認する
		WebDriverUtils.visibilityTimeout(By.tagName("h2"), 10);
		WebElement btn = WebDriverUtils.webDriver.findElement(By.tagName("h2"));
		assertThat(btn.getText(),containsString("あなたのスコア"));
		
		// エビデンスを取得する
		getEvidence(new Object(){});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 「戻る」ボタンを押下し試験開始画面に遷移後当該試験の結果が反映される")
	void test08() {
		// 「戻る」ボタンを押下する
		scrollTo("5000");
		WebDriverUtils.webDriver.findElement(By.xpath("//input[@value='戻る']")).click();
		
		// 画面遷移が正しく行われたか確認する
		WebDriverUtils.visibilityTimeout(By.tagName("h3"), 10);
		WebElement title = WebDriverUtils.webDriver.findElement(By.tagName("h3"));
		assertEquals("過去の試験結果", title.getText());
		
		// 試験結果が反映されていることを確認する
		List<WebElement> examResults = WebDriverUtils.webDriver.findElements(By.tagName("td"));
		WebElement examScore = null;
		for(WebElement examResult : examResults) {
			if((examResult.getText()).contains("点")) {
				examScore = examResult;
			}
		}
		assertEquals("0.0点", examScore.getText());
		
		// エビデンスを取得する
		scrollTo("400");
		getEvidence(new Object(){});
	}

}
