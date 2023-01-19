call gcloud components update

call gcloud config configurations create emulator
call gcloud config configurations activate emulator
call gcloud config set auth/disable_credentials true
call gcloud config set project newimgur
call gcloud config set api_endpoint_overrides/spanner http://localhost:9020/

call docker pull gcr.io/cloud-spanner-emulator/emulator
call docker run -d -p 9010:9010 -p 9020:9020 gcr.io/cloud-spanner-emulator/emulator

call set SPANNER_EMULATOR_HOST=localhost:9010

call gcloud spanner instances create newimgur-instance --config=emulator-config --description="Newimgur Instance" --nodes=1
call gcloud spanner databases create newimgur-database --instance newimgur-instance
call gcloud spanner databases ddl update newimgur-database --instance newimgur-instance --ddl "CREATE TABLE Images (id STRING(36) NOT NULL, createdAt TIMESTAMP, caption STRING(1024), fileType STRING(10)) PRIMARY KEY (id, createdAt DESC)"