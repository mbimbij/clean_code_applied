Feature:
  In order to view codecasts
  As a user
  I want see the list of codecasts and whether or not i bought it.

  Background:
    Given codecasts
      | title | publicationDate |
      | A     | 03/01/2014      |
      | B     | 03/02/2014      |
      | C     | 02/18/2014      |

  Scenario: no codecasts
    Given no codecasts
    And user "U"
    And user "U" logged in
    And there will be no codecasts presented

  Scenario: present viewable codecasts in chronological order
    Given user "U"
    And user "U" logged in
    And with licence for "U" able to view "A"
    Then then the following codecasts will be presented for "U"
      | title | publicationDate | viewable | downloadable |
      | C     | 02/18/2014      | false    | false        |
      | A     | 03/01/2014      | true     | false        |
      | B     | 03/02/2014      | false    | false        |

  Scenario: present downloadable codecasts in chronological order
    Given user "U"
    And user "U" logged in
    And with licence for "U" able to download "A"
    Then then the following codecasts will be presented for "U"
      | title | publicationDate | viewable | downloadable |
      | C     | 02/18/2014      | false    | false        |
      | A     | 03/01/2014      | false    | true         |
      | B     | 03/02/2014      | false    | false        |