
DB_NAME="flowwow"
DB_USER="postgres"
CONTAINER_NAME="flowwow-postgres"
BACKUP_DIR="./backups"
DATE=$(date +"%Y%m%d_%H%M%S")
BACKUP_FILE="$BACKUP_DIR/flowwow_backup_$DATE.sql"

# Создать папку для backup
mkdir -p $BACKUP_DIR

if ! docker ps | grep -q $CONTAINER_NAME; then
    echo "❌ Контейнер $CONTAINER_NAME не запущен"
    echo "Запустите контейнер: docker start $CONTAINER_NAME"
    exit 1
fi

echo "📦 Создание backup базы данных $DB_NAME..."

sudo docker exec -t $CONTAINER_NAME pg_dump -U $DB_USER $DB_NAME > $BACKUP_FILE


if [ $? -eq 0 ]; then
    echo "✅ Backup создан: $BACKUP_FILE"
    echo "📏 Размер: $(du -h $BACKUP_FILE | cut -f1)"
else
    echo "❌ Ошибка при создании backup"
    exit 1
fi

# Удалить старые backup (старше 30 дней)
find $BACKUP_DIR -name "flowwow_backup_*.sql" -mtime +30 -delete
echo "🗑️  Старые backup удалены"

echo ""
echo "📋 Список всех backup:"
ls -la $BACKUP_DIR/