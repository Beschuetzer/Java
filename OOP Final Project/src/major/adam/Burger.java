package major.adam;


public class Burger {
  private final IngredientList INGREDIENT_HASHMAP = new IngredientList();


  public Burger() {

  }

  public double getPrice(Ingredients ingredient) {
    try {
      return INGREDIENT_HASHMAP.getPrice(ingredient);
    } catch (Exception err) {
      System.out.println(err);
      return -1d;
    }
  }

}



DROP TABLE IF EXISTS address, employee;

CREATE TABLE employee (
    id INT,
    fname text NOT NULL,
    lname text NOT NULL,
    age INT,
    hiredate date,
    PRIMARY KEY(id)
);

INSERT INTO employee VALUES (01,  'Alan', 'Palmer',  32 , '12/15/2019');
INSERT INTO employee VALUES (02, 'Susan', 'Shepard',  28,  '07/21/2015');
INSERT INTO employee VALUES (03, 'Justin', 'Ward',   43, '08/24/2017');
INSERT INTO employee VALUES (04, 'Alan', 'Smith',  30,  '06/22/2017');
INSERT INTO employee VALUES (05, 'James', 'Betternot', 26, '06/22/17');
INSERT INTO employee VALUES (06, 'Ralph', 'White',  44,  '06/23/17');

CREATE TABLE address (
    id INT,
    address1 text NOT NULL,
    address2 text,
    city text NOT NULL,
    state char(2) NOT NULL,
    zip text NOT NULL,
    PRIMARY KEY(id)
);

INSERT INTO address VALUES (01, '1211 Sudan St', 'n/a', 'Mobile', 'AL', '36609');
INSERT INTO address VALUES (02, '4800 Barkshire Dr', 'n/a', 'Pace', 'FL', '32571');
INSERT INTO address VALUES (03, '12 Nutmeg Ct', 'n/a', 'Culver City', 'CA', '90211');
INSERT INTO address VALUES (04, '2142 Elmwood Pl', 'n/a', 'Johnson City', 'TN', '37112');
INSERT INTO address VALUES (05, '777 Heavenly Ln', 'Box 13', 'Pike City', 'MN', '50877');

CREATE TABLE contact (
    id INT,
    cellphone text,
    homephone text,
    email text,
    PRIMARY KEY(id)
);

INSERT INTO contact VALUES (01, '5121325343', '5125234234', 'apalmer@yachtmail.com');
INSERT INTO contact VALUES (02, '5129739834', '5129847873', 'sshepard@yorkdevtraining.com');
INSERT INTO contact VALUES (03, '6453898502', '6459872345', ' jsward2007@yahoo.com');
INSERT INTO contact VALUES (04, '8763238756', '8763736548', 'alsmith999@gmail.com');
INSERT INTO contact VALUES (05, '8880345966', '8888567987', 'james.betternot@hotmail.com');
INSERT INTO contact VALUES (06, '3322827765', '3328760098', 'ralph.white264@aol.com');


SELECT e.fname, e.lname, e.age, a.city, a.state
FROM employee as e
INNER JOIN address as a ON e.id = a.id
WHERE e.fname = 'Alan';

SELECT e.fname, e.lname, e.age, a.city, a.state, c.email
FROM employee as e
INNER JOIN address as a ON e.id = a.id
INNER JOIN contact as c ON e.id = c.id
WHERE c.email = 'james.betternot@hotmail.com';

UPDATE contact
SET cellphone = '4383991212'
WHERE (
  SELECT id
  FROM employee as e
  WHERE e.fname = 'Susan' and e.lname = 'Shepard'
) = id;