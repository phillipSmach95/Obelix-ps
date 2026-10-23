The test supplies a **fake quarry** with sample menhirs.

But `ObelixWebshopService` ignored that fake and used `WebClient` to contact a real quarry at `localhost:8081`. Nothing was running there, so the connection failed and returned **500**.

Now the service uses the injected `QuarryApi`:
- During tests, it uses the fake quarry.
- During normal operation, it calls the real quarry.

We also fixed a mismatched URL and ensured an empty basket returns **400 Bad Request**.

Zipkin only shows services that have reported traces. The webshop test uses a fake quarry inside the webshop, so it does not call a separate quarry service.

To see both services, start Zipkin and run both applications normally in separate terminals:

```powershell
.\gradlew.bat :obelix-quarry-impl:bootRun
.\gradlew.bat :obelix-webshop:bootRun
```

Then open `http://localhost:8080/api/menhirs` and refresh Zipkin at `http://localhost:9411`.

The webshop must use Spring's injected `WebClient.Builder` to pass the trace ID to the quarry. Creating a builder with `WebClient.builder()` bypasses that setup. Tracing dependencies come from `obelix-common`, with versions managed by Spring Boot.
