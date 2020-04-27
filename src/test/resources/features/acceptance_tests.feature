Feature:
  In order to view codecasts
  As a user
  I want see the list of codecasts and whether or not i bought it.

  Background:
    Given codecasts
      | title | published  |
      | A     | 03/01/2014 |
      | B     | 03/02/2014 |
      | C     | 02/18/2014 |

  Scenario: test Scenario:
    Given no codecasts
    And user U logged in
    Then then the following codecasts will be presented for U
    And there will be no codecasts presented

