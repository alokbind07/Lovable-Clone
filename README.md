### Building Lovable Clone

Lovable Clone is an AI-driven code generation platform that allows users to create complete React applications using simple natural-language prompts. Inspired by tools like Lovable and v0.dev, the platform converts user ideas (for example, “Build a snake game in React”) into production-ready frontend code in real time.

The system is designed with scalability and performance in mind, leveraging a modern backend architecture to stream AI-generated code, manage projects, and handle high-concurrency usage efficiently. It aims to simplify application development by reducing manual coding effort and accelerating the idea-to-implementation workflow.

## System Architecture

![Lovable Clone System Architecture]

<img width="912" height="817" alt="system-architecture" src="https://github.com/user-attachments/assets/7f5e0139-a356-477c-90e3-9d343bbe40a2" />


> High-level workflow illustrating prompt ingestion, AI-driven code generation, storage, and isolated execution.

### Architecture Overview

Lovable Clone is built on a distributed, microservices-based architecture optimized for real-time AI code generation, scalability, and secure execution.

The system converts natural-language prompts into fully functional React applications while maintaining conversational context, codebase awareness, and execution isolation.

### Request Flow

1. **Prompt Ingestion**
   - Users submit prompts through the frontend interface.
   - Requests are routed via **Spring Cloud API Gateway**, providing centralized authentication, routing, and rate limiting.

2. **Intelligence & Orchestration**
   - The `intelligence-service` acts as the core orchestrator.
   - It combines:
     - System prompts
     - Recent session history (last N messages)
     - Workspace context
   - The enriched prompt is sent to the **LLM** for incremental code generation.

3. **Retrieval-Augmented Generation (RAG)**
   - Existing project files are chunked, embedded, and stored in **Qdrant Vector Database**.
   - During generation, similarity search retrieves relevant code snippets to improve consistency and reduce hallucinations.

4. **Real-Time Code Streaming**
   - Generated code is streamed back to the client using **Server-Sent Events (SSE)**.
   - Files such as `index.html` are created and updated incrementally, enabling live feedback.

5. **Storage & Persistence**
   - Generated artifacts are buffered and persisted in **MinIO Object Storage**.
   - This ensures durability, version continuity, and fast retrieval for previews.

6. **Execution & Preview**
   - Each project is deployed into an isolated **Kubernetes namespace**.
   - The `execution-service` provisions a dedicated micro VM–based pod for secure runtime execution.
   - Users receive a unique preview URL to interact with the running application.

### Key Architectural Benefits

- **Horizontal scalability** via stateless services and Kubernetes
- **Low-latency streaming** with SSE
- **Strong isolation** using per-project execution environments
- **Improved generation accuracy** through RAG-based context injection

## Database Design (ER Diagram)

![Lovable Clone ER Diagram]

<img width="5804" height="2820" alt="ER_Diagram" src="https://github.com/user-attachments/assets/1c660784-57f8-4675-8e85-99e76d0175b6" />

> Entity-relationship model supporting users, projects, AI conversations, subscriptions, and execution lifecycle.

### Schema Overview

The database schema is designed to support collaborative AI-driven application development, subscription-based access control, and execution tracking at scale.

### Core Domains

#### User & Access Management
- **User**: Represents a platform user with authentication and profile metadata.
- **Subscription / Plan**: Manages billing, usage limits, and feature entitlements via Stripe.
- **Usage_Log**: Tracks token usage, latency, and actions for monitoring and quota enforcement.

#### Project & Collaboration
- **Project**: Logical container for a generated application.
- **Project_Ownership**: Defines project ownership semantics.
- **Project_Member**: Enables multi-user collaboration with role-based access (EDITOR, VIEWER).
- **Project_File**: Stores generated source file metadata, with content persisted in object storage.

#### AI Conversation Layer
- **Chat_Session**: Groups AI interactions per project.
- **Chat_Message**: Stores prompts, responses, tool calls, and token usage for conversational continuity.

#### Execution & Preview
- **Preview**: Tracks live execution instances, Kubernetes namespaces, and preview URLs.

This schema ensures strong data consistency between conversational context, generated artifacts, billing constraints, and runtime execution.

## Subscription & Payment System

Lovable Clone implements a subscription-based payment model using **Stripe**, enabling secure billing, plan management, and usage-based access control.

The integration follows Stripe’s recommended best practices for SaaS applications and supports scalable subscription lifecycle management.

### Key Capabilities

- **Subscription Plans**
  - Multiple plans are supported with configurable limits (projects, tokens, previews).
  - Each plan is mapped to a Stripe Price ID for billing synchronization.

- **Secure Checkout**
  - Users are redirected to Stripe Checkout for secure payment processing.
  - Sensitive card data is handled entirely by Stripe, ensuring PCI compliance.

- **Subscription Lifecycle Management**
  - Active, canceled, and expired subscriptions are tracked in the system.
  - Billing periods, renewal status, and cancellation-at-period-end are persisted.

- **Webhook-Based Synchronization**
  - Stripe webhooks are used to listen for subscription events such as:
    - `checkout.session.completed`
    - `customer.subscription.updated`
    - `customer.subscription.deleted`
  - Webhooks ensure reliable state synchronization between Stripe and the application database.

- **Usage Enforcement**
  - User entitlements are enforced based on the active subscription plan.
  - Token usage and API consumption are monitored and logged for quota control.

### Data Flow

1. User selects a subscription plan.
2. Backend creates a Stripe Checkout Session.
3. User completes payment on Stripe-hosted checkout.
4. Stripe sends webhook events to update subscription state.
5. Application updates user access and usage limits accordingly.

> Stripe integration is implemented using the official Stripe APIs and follows the guidelines described in the Stripe documentation: https://docs.stripe.com/

> Payment workflows are designed to be idempotent and webhook-driven to ensure consistency under retries and network failures.




