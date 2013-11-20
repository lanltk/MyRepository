package org.exoplatform.selenium.platform.forum.functional;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.forum.ForumBase;
import org.exoplatform.selenium.platform.forum.ForumManageCategory;
import org.exoplatform.selenium.platform.forum.ForumManageForum;
import org.exoplatform.selenium.platform.forum.ForumManageTopic;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author lanltk
 * @date 20 Nov 2013
 */
public class Forum_Forum_Category_ExportAll extends ForumBase {

	ManageAccount magAc;
	ForumManageCategory magCat;
	ForumManageForum magForum;
	ForumManageTopic magTopic;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		getDriverAutoSave();
		magAc = new ManageAccount(driver);
		magCat = new ForumManageCategory(driver);
		magForum = new ForumManageForum(driver);
		magTopic = new ForumManageTopic(driver);
		
		magAc.signIn("john", "gtn");
		goToForums();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**CaseId: 72669 -> Functional-Forum-Forum-Category-Export all categories
	 * 
	 */
	@Test
	
	public void test01_ExportAllCategoryForumTopic(){
		String catName1 = "CategoryForum_01";
		String[] restricted1 = {"demo"};
		String description1 = "Add new category 01";
		
		String catName2 = "CategoryForum_02";
		String[] restricted2 = {"demo"};
		String description2 = "Add new category 02";
		
		String forumName1 = "Forum_01";
		String forumName2 = "Forum_02";
		String topicName1 = "Topic_01";
		String topicName2 = "Topic_02";
		String fileName = "FileExportCategories";
		
		magCat.addNewCategoryInForum(catName1, "1", 1, restricted1, description1, 0, null);
		magForum .quickAddForum(forumName1);
		magTopic .quickStartTopic(topicName1,"Start topic_01");
		magTopic .quickStartTopic(topicName2,"Start topic_02");
		
		click(By.linkText(catName1));
		magForum .quickAddForum(forumName2);
		magTopic .quickStartTopic(topicName1,"Start topic_01");
		magTopic .quickStartTopic(topicName2,"Start topic_02");
		
		goToForumHome();
		magCat.addNewCategoryInForum(catName2, "2", 1, restricted2, description2, 0, null);
		magForum .quickAddForum(forumName1);
		magTopic .quickStartTopic(topicName1,"Start topic_01");
		magTopic .quickStartTopic(topicName2,"Start topic_02");
		
		click(By.linkText(catName2));
		magForum .quickAddForum(forumName2);
		magTopic .quickStartTopic(topicName1,"Start topic_01");
		magTopic .quickStartTopic(topicName2,"Start topic_02");
		
		goToForumHome();

		magCat .exportCategoryInForum(fileName, true,"");
		Utils.pause(3000);
		assert checkFileExisted("TestOutput/"+fileName + ".zip");
        
		/* Delete all categories, Forum and topic before running Import test case*/
		click(By.linkText(catName1));
		magCat .deleteCategoryInForum(catName1);
		click(By.linkText(catName2));
		magCat .deleteCategoryInForum(catName2);
		
	}
	


}