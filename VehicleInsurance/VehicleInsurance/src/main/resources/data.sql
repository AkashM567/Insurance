

CREATE TABLE policyholder (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  address VARCHAR(255) NOT NULL,
  contact_details VARCHAR(255) NOT NULL
);
INSERT INTO policyholder(name, address, contact_details) values('Akash','Sathyamangalam','7708923715');

CREATE TABLE agent (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  contact_details VARCHAR(255) NOT NULL,
  commission_percentage DECIMAL(10,2) NOT NULL
);
INSERT INTO agent(name, contact_details, email, commission_percentage)
				values('Vijay', 'Chennai','uniq@1234', 10.0);

CREATE TABLE policy (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  policy_number VARCHAR(50) NOT NULL UNIQUE,
  policy_type VARCHAR(50) NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  agent_id BIGINT NOT NULL,
  policy_holder_id BIGINT NOT NULL,
  FOREIGN KEY (agent_id) REFERENCES agent(id),
  FOREIGN KEY (policy_holder_id) REFERENCES policyholder(id)
);
INSERT INTO policy(policy_number, policy_type, start_date, end_date, amount, agent_id, policy_holder_id)
				values('1001', 'VehicleInsurance', '2024-01-01', '2024-02-01',10000,1,1),
					  ('1002', 'VehicleInsurance', '2023-01-01', '2022-01-01',20000,1,1);

CREATE TABLE payment (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  policy_id BIGINT NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  payment_date DATE NOT NULL,
  FOREIGN KEY (policy_id) REFERENCES policy(id)
);

CREATE TABLE claim_detail (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  policy_id BIGINT NOT NULL,
  name VARCHAR(255) NOT NULL,
  contact_details VARCHAR(255) NOT NULL,
  reason TEXT NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (policy_id) REFERENCES policy(id)
);
