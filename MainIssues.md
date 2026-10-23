The test supplies a **fake quarry** with sample menhirs.

But `ObelixWebshopService` ignored that fake and used `WebClient` to contact a real quarry at `localhost:8081`. Nothing was running there, so the connection failed and returned **500**.

Now the service uses the injected `QuarryApi`:
- During tests, it uses the fake quarry.
- During normal operation, it calls the real quarry.

We also fixed a mismatched URL and ensured an empty basket returns **400 Bad Request**.