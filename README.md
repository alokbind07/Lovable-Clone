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

## Features

### AI-Driven Application Generation
- Generate complete React applications from natural-language prompts.
- Supports iterative refinement through conversational context.
- Streams AI-generated code in real time using Server-Sent Events (SSE).

### Subscription-Based Access Control
- Feature access is governed by active subscription plans.
- Different plans define limits on:
  - Number of projects
  - Daily AI token usage
  - Concurrent previews
- Subscription state is continuously synchronized with Stripe via webhooks.

### Secure Stripe Payments
- Integrated Stripe Checkout for secure, PCI-compliant payments.
- Supports subscription lifecycle events including activation, renewal, and cancellation.
- Webhook-driven updates ensure billing and access consistency.

### Usage Monitoring & Quota Enforcement
- Tracks AI token usage, request latency, and actions per user.
- Enforces plan-based limits in real time to prevent overuse.
- Usage logs enable transparency and future billing analytics.

### Project & Workspace Management
- Create and manage multiple projects per user.
- Ownership and collaboration support with role-based access (Editor, Viewer).
- Project visibility can be controlled via public/private settings.

### Retrieval-Augmented Generation (RAG)
- Existing project files are embedded and stored in a vector database.
- Relevant code context is retrieved during generation to improve accuracy and consistency.
- Reduces hallucinations and maintains architectural coherence.

### Real-Time Preview & Execution
- Each project is executed in an isolated Kubernetes namespace.
- Secure micro-VM–based execution ensures strong isolation.
- Users receive a live preview URL for instant feedback.

### Persistent Storage & Versioning
- Generated files are stored in object storage (MinIO).
- Enables durable project state and fast preview reloads.
- Metadata and relationships are managed via a relational database.

### Scalable & Production-Ready Architecture
- Microservices-based backend with centralized API Gateway.
- Horizontally scalable services designed for high concurrency.
- Stateless execution with durable storage for reliability.

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

## AI Code Generation System Architecture & Spring AI Integration

This section documents the enterprise-grade architecture and integration approach used for the AI-driven code generation system in the Lovable Clone project. The design focuses on scalability, observability, resilience, and clear separation of responsibilities across system layers.

---

## System Architecture Diagram

![AI Code Generation System Architecture]<img width="3701" height="2265" alt="ai_design_architecture" src="https://github.com/user-attachments/assets/9c6dd5b1-bc45-4552-be44-e076287bb304" />

## Architectural Overview

The AI Code Generation System is composed of the following logical components:

1. Client Interface (Frontend)
2. Backend Orchestration Service (Spring Boot)
3. AI Execution Layer (Spring AI + OpenRouter)
4. Context and Storage Layer (MinIO + Database)
5. Tooling and Reliability Layer (File Tools, Circuit Breakers, Streaming Pipeline)

The system is designed to provide context-aware AI-assisted development workflows by dynamically injecting repository structure and file content into model prompts.

---

## Step-by-Step Architecture Flow

### 1. User Interaction and Prompt Submission

The process begins in the frontend client where the user submits a natural language request, such as code generation, refactoring, or repository analysis. The request is transmitted to the backend via an HTTP POST request.

The frontend also supports streaming responses, enabling real-time rendering of AI-generated output.

---

### 2. Backend Prompt Orchestration (Spring Boot)

The Spring Boot service acts as the orchestration layer. It performs the following responsibilities:

* Accepts the user prompt.
* Applies system-level guardrails, constraints, and coding standards.
* Constructs a composite prompt consisting of system prompts, user input, repository metadata, and file contents.
* Initiates streaming communication with the AI model.

This layer also buffers streaming chunks and tracks token usage and metadata for observability and billing.

---

### 3. Repository Context Ingestion (File Tree and Content Resolution)

To enable context-aware code generation, the backend retrieves:

* Repository file tree structure.
* Selected file contents based on relevance.

File content is retrieved using a dedicated tool interface that accepts file path lists and returns structured content. A circuit breaker mechanism protects the system from large or failing repository fetch operations.

The effective model input context is constructed as:
System Prompt + User Prompt + File Tree + File Content

---

### 4. AI Model Invocation via Spring AI and OpenRouter

Spring AI is used as the abstraction layer for interacting with large language models. OpenRouter acts as the model gateway, enabling dynamic selection of models based on performance and cost requirements.

Spring AI handles:

* Model configuration and lifecycle
* Prompt execution
* Streaming token-level responses
* Error handling and retries

Reference for model catalog:
[https://openrouter.ai/models](https://openrouter.ai/models)

---

### 5. Object Storage and Template Management (MinIO)

MinIO is used as S3-compatible object storage for:

* Code templates
* Repository snapshots
* Generated code artifacts
* Intermediate AI outputs

This separation ensures that large file artifacts are not stored directly in the relational database, improving scalability and performance.

Reference for MinIO:
[https://www.min.io/](https://www.min.io/)

---

### 6. System Prompt Governance

System prompts define global AI behavior, coding rules, output formats, and safety constraints. These prompts are centrally managed and version-controlled to ensure consistent AI behavior across environments.

Reference system prompt repository:
[https://github.com/x1xhlol/system-prompts-and-models-of-ai-tools](https://github.com/x1xhlol/system-prompts-and-models-of-ai-tools)

---

### 7. Streaming Response Pipeline

The backend streams AI responses to the frontend in real time. The streaming pipeline includes:

* Chunk buffering for partial responses
* Parsing of structured AI output
* Token usage and latency monitoring

This approach provides immediate feedback to developers and supports interactive AI-driven development workflows.

---

### 8. Reliability, Resilience, and Observability

The system incorporates enterprise reliability patterns, including:

* Circuit breakers for file retrieval and tool execution
* Separation of metadata and binary storage
* Centralized logging of AI prompts and responses
* Usage metrics and monitoring for AI operations

These mechanisms ensure system stability under high load and during partial failures.

---

## Spring AI Integration Strategy

Spring AI serves as the core integration framework for AI capabilities within the Spring Boot ecosystem. It provides a consistent API for interacting with external AI providers and supports enterprise deployment requirements.

Key responsibilities of Spring AI in this architecture include:

* Unified AI provider configuration
* Prompt orchestration and execution
* Streaming AI responses
* Integration with Spring Boot security, configuration, and observability stacks

---

## Enterprise Design Considerations

The architecture is designed with the following enterprise principles:

* Modularity and separation of concerns
* Vendor-agnostic AI provider integration
* Scalable object storage for large artifacts
* Real-time streaming for developer experience
* Governance of system prompts and AI behavior
* Resilience patterns for external tool dependencies

---

This architecture enables a production-ready AI code generation platform suitable for internal developer platforms, enterprise copilots, and AI-assisted software engineering systems.



