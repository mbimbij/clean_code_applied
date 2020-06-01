Feature:
  In order to view codecasts
  As a user
  I want see the list of codecasts and whether or not i bought it.

  Background:
    Given codecasts
      | title | publicationDate | permalink |
      | A     | 03/01/2014      | episode-1 |

  Scenario: present show episode
    Given user "U"
    And user "U" logged in
    When the user requests for episode "episode-1"
    Then then the presented title is
      | title | publicationDate |
      | A     | 03/01/2014      |
    And with option to purchase "VIEW" license
    And with option to purchase "DOWNLOAD" license