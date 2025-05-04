# f_dstr
# **Projeto de CI/CD com Kubernetes**  

Este projeto implementa um pipeline de **CI/CD automatizado** usando:  
- **GitHub Actions** para integração contínua  
- **Docker** para containerização  
- **Kubernetes (Kind)** para orquestração em staging/production  

---

## **📋 Pré-requisitos**  
✅ **Git** instalado ([Download](https://git-scm.com/))  
✅ **Docker** ([Install Guide](https://docs.docker.com/get-docker/))  
✅ **Kind** (Kubernetes local) ([Install](https://kind.sigs.k8s.io/docs/user/quick-start/))  
✅ **kubectl** ([Install](https://kubernetes.io/docs/tasks/tools/))  
✅ Conta no **Docker Hub** (para push das imagens)  

---

## **🚀 Como Executar o Projeto**  

### **1. Clone o Repositório**  
```bash
git clone https://github.com/christian-prants/f_dstr.git
```

### **2. Configure as Secrets no GitHub**  
Vá em **Settings > Secrets > Actions** e adicione:  
- `DOCKER_HUB_USERNAME` → Seu usuário Docker Hub  
- `DOCKER_HUB_TOKEN` → Token de acesso ([Como gerar](https://docs.docker.com/docker-hub/access-tokens/))  
- `KUBE_CONFIG_STAGING` → Config do Kubernetes para staging 
- `KUBE_CONFIG_PRODUCTION` → Config do Kubernetes para production  

Se usar **Kind local**, gere os kubeconfigs com:  
```bash
kind get kubeconfig --name staging > kubeconfig-staging.yaml
kind get kubeconfig --name production > kubeconfig-production.yaml
```

### **3. Inicie os Clusters Kubernetes (Local com Kind)**  
```bash
# Cria cluster de staging
kind create cluster --name staging

# Cria cluster de production
kind create cluster --name production
```

### **4. Execute o Pipeline CI/CD**  
- O pipeline é **acionado automaticamente** ao fazer push para `main`  
- Ou execute manualmente em **Actions > CI/CD Pipeline > Run workflow**  

---

## **🛠️ Estrutura do Projeto**  
```
.
├── .github/workflows/
│   └── ci-cd.yml            # Config do GitHub Actions
├── k8s/
│   ├── staging-deployment.yaml  # Kubernetes manifests
│   ├── staging-service.yaml
│   ├── production-deployment.yaml
│   └── production-service.yaml
├── src/                     # Código-fonte Java
├── Dockerfile               # Config de containerização
├── pom.xml                  # Dependências Maven
└── README.md                # Este arquivo
```

---

## **🔍 Monitoramento**  
Para verificar os deploys:  

### **Staging**  
```bash
kubectl --context kind-staging get pods -n staging
```

### **Production**  
```bash
kubectl --context kind-production get pods -n production
```

---

## **📌 Troubleshooting**  
| **Problema**               | **Solução**                          |
|----------------------------|--------------------------------------|
| Erro no build do Maven     | Verifique `pom.xml` e JDK 11         |
| Falha no push do Docker    | Confira `DOCKER_HUB_TOKEN`           |
| Kubernetes não responde    | Reinicie o Kind: `kind delete cluster` |

---

## **📄 Licença**  
MIT License. Consulte [LICENSE](LICENSE).