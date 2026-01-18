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

