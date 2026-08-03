# Build stage
FROM golang:1.21-alpine AS builder

WORKDIR /app

# Download dependencies
COPY go.mod ./
# COPY go.sum ./ (Descomente quando o arquivo existir)
RUN go mod download

# Copy source code and build
COPY . .
RUN CGO_ENABLED=0 GOOS=linux go build -o /app/worker main.go

# Run stage
FROM alpine:latest

WORKDIR /app
# Run as non-root user
RUN addgroup -S worker && adduser -S worker -G worker
USER worker:worker

COPY --from=builder /app/worker .

CMD ["./worker"]
