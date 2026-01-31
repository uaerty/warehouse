# Questions

Here we have 3 questions related to the code base for you to answer. It is not about right or wrong, but more about what's the reasoning behind your decisions.

1. In this code base, we have some different implementation strategies when it comes to database access layer and manipulation. If you would maintain this code base, would you refactor any of those? Why?

**Answer:**
```txt
Yes, I would refactor — not because the code is broken, but because it mixes styles. I’d make repositories consistent, move mapping into dedicated mappers, keep validations strictly in the service layer, and enforce clear separation between entity, domain, and DTO. 
That way, the code base is easier to maintain, test, and extend when new features (like fulfillment constraints) are added.
```
----
2. When it comes to API spec and endpoints handlers, we have an Open API yaml file for the `Warehouse` API from which we generate code, but for the other endpoints - `Product` and `Store` - we just coded directly everything. What would be your thoughts about what are the pros and cons of each approach and what would be your choice?

**Answer:**
```txt
If I were maintaining this code base long‑term, I’d lean toward standardizing on OpenAPI for all services (Warehouse, Product, Store).

It enforces consistency across endpoints.

It provides auto‑generated docs and client SDKs.

It makes onboarding new developers easier.

It reduces the risk of divergence in style and error handling.

That said, for rapid prototyping or internal APIs, hand‑coding can be fine — but once the API is exposed to multiple consumers (frontend apps, external partners, mobile clients), the benefits of OpenAPI outweigh the overhead.
```
----
3. Given the need to balance thorough testing with time and resource constraints, how would you prioritize and implement tests for this project? Which types of tests would you focus on, and how would you ensure test coverage remains effective over time?

**Answer:**
```txt
When planning tests for this project, the most important thing is to focus first on the business rules that keep the system correct, such as making sure warehouses have unique business unit codes, locations are valid, capacity and stock limits are respected, and fulfillment rules are enforced. 
These should be covered with unit tests because they are the foundation of the application and must never break. Next, integration tests should check that the database queries and repositories return the right results, and that the API endpoints behave correctly, including returning proper error codes when rules are violated. 
On top of that, a few end‑to‑end tests should simulate full workflows, like creating a store, adding warehouses, and assigning products until the limits are reached, to prove the system works together as expected. 
Over time, test coverage can be kept effective by following the test pyramid approach (lots of unit tests, fewer integration tests, very few end‑to‑end tests), running tests automatically in CI/CD, adding new tests whenever bugs are fixed, and using coverage tools to make sure the most important parts of the code are tested. 
This way, you balance thoroughness with efficiency and ensure the system remains reliable without wasting resources.
```