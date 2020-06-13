Feature: validate page actions

Scenario: assembly test scenario

	Given Launch browser and navigate to join assembly website.
		
	Then Search email keyword and return list of count.
		
	Then Enter abc@gmail.com, click on Try for Free and test.
		
	Then Enter abc@carrothr.com, click on Try for Free and test.
		
	And Navigate to feature page.
		
	Then Validate Recognition tab should be highlighted.
		
	Then Click on Anniversaries and Birthdays should enable this tab and disable all other tabs.
		
	And Scroll to footer and click on Contact Us under Support.
		
	Then Enter Slack in Find an answer yourself field and verify the results displayed.