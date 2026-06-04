# secret/

외부에 노출되면 안 되는 인증 파일을 두는 폴더입니다.
`.gitignore`에 의해 **이 폴더의 내용물은 저장소에 올라가지 않습니다** (이 README 제외).

## 필요한 파일

- `devhub-fjly-XXXXXXXX.json` — Google Cloud Dialogflow 서비스 계정 키
  - 발급: GCP 콘솔 → IAM 및 관리자 → 서비스 계정 → 키 → 새 키 만들기(JSON)
  - 받은 JSON을 이 폴더에 넣으세요.
  - `application.properties` 의 경로를 파일명에 맞추세요:
    `dialogflow.credentials.path=classpath:secret/<파일명>.json`

> ⚠️ 이전에 저장소/정적 경로에 노출되었던 키는 **반드시 폐기(revoke) 후 재발급**하여 교체하세요.
