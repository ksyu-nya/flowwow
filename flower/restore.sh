DB_NAME="flowwow"
DB_USER="postgres"
CONTAINER_NAME="flowwow-postgres"
BACKUP_FILE=$1

if [ -z "$BACKUP_FILE" ]; then
    echo "❌ Укажите файл для восстановления"
    echo "Использование: ./restore.sh backup_file.sql"
    exit 1
fi

if [ ! -f "$BACKUP_FILE" ]; then
    echo "❌ Файл $BACKUP_FILE не найден"
    exit 1
fi

if ! docker ps | grep -q $CONTAINER_NAME; then
    echo "❌ Контейнер $CONTAINER_NAME не запущен"
    echo "Запустите контейнер: docker start $CONTAINER_NAME"
    exit 1
fi

echo "🔄 Восстановление базы данных $DB_NAME из $BACKUP_FILE..."

# Восстановить из backup (через docker exec)
sudo at "$BACKUP_FILE" | docker exec -i $CONTAINER_NAME psql -U $DB_USER -d $DB_NAME

if [ $? -eq 0 ]; then
    echo "✅ База данных успешно восстановлена"

    echo ""
    echo "📊 Проверка данных:"
    docker exec -i $CONTAINER_NAME psql -U $DB_USER -d $DB_NAME -c "SELECT COUNT(*) as users_count FROM users;"
    docker exec -i $CONTAINER_NAME psql -U $DB_USER -d $DB_NAME -c "SELECT COUNT(*) as items_count FROM items;"
else
    echo "❌ Ошибка при восстановлении"
    exit 1
fi