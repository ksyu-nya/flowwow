// import React from 'react';
// import { Link } from 'react-router-dom';
// import { useAuth } from '../../context/AuthContext';
//
// export default function AdminDashboard() {
//   const { user } = useAuth();
//
//   const menuItems = [
//     { to: '/admin/items', icon: 'fa-box', title: 'Управление букетами', desc: 'Добавляйте, редактируйте и удаляйте букеты' },
//     { to: '/admin/orders', icon: 'fa-truck', title: 'Заказы', desc: 'Просмотр и управление заказами' },
//     { to: '/admin/users', icon: 'fa-users', title: 'Пользователи', desc: 'Управление пользователями' },
//   ];
//
//   return (
//     <div style={{ padding: '40px 0', background: '#f5f5f5', minHeight: 'calc(100vh - 200px)' }}>
//       <div className="container">
//         <div style={{ marginBottom: '40px' }}>
//           <h1 style={{ fontSize: '32px', marginBottom: '10px' }}>Добро пожаловать, {user?.name || 'Администратор'}!</h1>
//           <p>Управляйте интернет-магазином цветов</p>
//         </div>
//
//         <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))', gap: '20px', marginBottom: '50px' }}>
//           <div style={{ background: 'white', borderRadius: '15px', padding: '25px', display: 'flex', alignItems: 'center', gap: '20px' }}>
//             <i className="fas fa-box" style={{ fontSize: '48px', color: '#e83e8c' }}></i>
//             <div>
//               <h3 style={{ fontSize: '14px', color: '#666' }}>Всего букетов</h3>
//               <p style={{ fontSize: '28px', fontWeight: 'bold' }}>12</p>
//             </div>
//           </div>
//           <div style={{ background: 'white', borderRadius: '15px', padding: '25px', display: 'flex', alignItems: 'center', gap: '20px' }}>
//             <i className="fas fa-shopping-cart" style={{ fontSize: '48px', color: '#e83e8c' }}></i>
//             <div>
//               <h3 style={{ fontSize: '14px', color: '#666' }}>Заказов сегодня</h3>
//               <p style={{ fontSize: '28px', fontWeight: 'bold' }}>8</p>
//             </div>
//           </div>
//           <div style={{ background: 'white', borderRadius: '15px', padding: '25px', display: 'flex', alignItems: 'center', gap: '20px' }}>
//             <i className="fas fa-users" style={{ fontSize: '48px', color: '#e83e8c' }}></i>
//             <div>
//               <h3 style={{ fontSize: '14px', color: '#666' }}>Пользователей</h3>
//               <p style={{ fontSize: '28px', fontWeight: 'bold' }}>156</p>
//             </div>
//           </div>
//         </div>
//
//         <div>
//           <h2 style={{ marginBottom: '20px' }}>Управление</h2>
//           <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(280px, 1fr))', gap: '20px' }}>
//             {menuItems.map(item => (
//               <Link key={item.to} to={item.to} style={{ background: 'white', borderRadius: '15px', padding: '30px', textAlign: 'center', textDecoration: 'none', color: '#333', transition: 'all 0.3s' }}>
//                 <i className={`fas ${item.icon}`} style={{ fontSize: '48px', color: '#e83e8c', marginBottom: '15px' }}></i>
//                 <h3>{item.title}</h3>
//                 <p style={{ color: '#666', fontSize: '14px' }}>{item.desc}</p>
//               </Link>
//             ))}
//           </div>
//         </div>
//       </div>
//     </div>
//   );
// }

import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getItemById, createItem, updateItem } from '../../api/items';
import { Button } from '../../components/UI/Button';
import { Loader } from '../../components/UI/Loader';

const categories = [
    { value: 'romantic', label: 'Романтические' },
    { value: 'wedding', label: 'Свадебные' },
    { value: 'spring', label: 'Весенние' },
    { value: 'birthday', label: 'День рождения' },
    { value: 'business', label: 'Деловые' },
];

export default function AdminItemForm() {
    const { id } = useParams();
    const navigate = useNavigate();
    const isEdit = !!id;

    // Инициализируем с явными значениями (не undefined!)
    const [formData, setFormData] = useState({
        name: '',
        description: '',
        price: '',
        category: 'romantic',
        is_hit: false,   // ← явно false, а не undefined
        is_new: false,   // ← явно false, а не undefined
        image_url: '',
    });
    const [loading, setLoading] = useState(false);
    const [saving, setSaving] = useState(false);

    useEffect(() => {
        if (isEdit) {
            loadItem();
        }
    }, [id]);

    const loadItem = async () => {
        try {
            setLoading(true);
            const item = await getItemById(id);
            setFormData({
                name: item.name || '',
                description: item.description || '',
                price: item.price || '',
                category: item.category || 'romantic',
                is_hit: item.is_hit ?? false,    // ← используем ?? на случай undefined
                is_new: item.is_new ?? false,    // ← используем ?? на случай undefined
                image_url: item.image_url || '',
            });
        } catch (error) {
            console.error('Ошибка загрузки:', error);
            alert('Не удалось загрузить данные букета');
        } finally {
            setLoading(false);
        }
    };

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: type === 'checkbox' ? checked : value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!formData.name.trim()) {
            alert('Введите название букета');
            return;
        }
        if (!formData.price || formData.price <= 0) {
            alert('Введите корректную цену');
            return;
        }
        if (!formData.image_url.trim()) {
            alert('Введите ссылку на изображение');
            return;
        }

        try {
            setSaving(true);

            const dataToSave = {
                name: formData.name,
                description: formData.description,
                price: Number(formData.price),
                image_url: formData.image_url,
                category: formData.category,
                is_hit: formData.is_hit,
                is_new: formData.is_new,
            };

            if (isEdit) {
                await updateItem(id, dataToSave);
                alert('Букет успешно обновлен');
            } else {
                await createItem(dataToSave);
                alert('Букет успешно создан');
            }

            navigate('/admin/items');
        } catch (error) {
            console.error('Ошибка сохранения:', error);
            alert('Ошибка при сохранении букета');
        } finally {
            setSaving(false);
        }
    };

    if (loading) return <Loader />;

    return (
        <div style={{ padding: '40px 0', background: '#f5f5f5', minHeight: 'calc(100vh - 200px)' }}>
            <div className="container">
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '30px' }}>
                    <h1>{isEdit ? 'Редактировать букет' : 'Добавить новый букет'}</h1>
                    <Button variant="outline" onClick={() => navigate('/admin/items')}>← Назад</Button>
                </div>

                <form onSubmit={handleSubmit} style={{ background: 'white', borderRadius: '15px', padding: '30px' }}>
                    <div style={{ marginBottom: '20px' }}>
                        <label style={{ display: 'block', marginBottom: '8px', fontWeight: '500' }}>Название букета *</label>
                        <input
                            type="text"
                            name="name"
                            value={formData.name}
                            onChange={handleChange}
                            placeholder="Например: Романтический букет"
                            style={{ width: '100%', padding: '12px', border: '1px solid #ddd', borderRadius: '8px' }}
                            required
                        />
                    </div>

                    <div style={{ marginBottom: '20px' }}>
                        <label style={{ display: 'block', marginBottom: '8px', fontWeight: '500' }}>Описание</label>
                        <textarea
                            name="description"
                            value={formData.description}
                            onChange={handleChange}
                            rows="3"
                            placeholder="Краткое описание букета"
                            style={{ width: '100%', padding: '12px', border: '1px solid #ddd', borderRadius: '8px' }}
                        />
                    </div>

                    <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px', marginBottom: '20px' }}>
                        <div>
                            <label style={{ display: 'block', marginBottom: '8px', fontWeight: '500' }}>Цена (₽) *</label>
                            <input
                                type="number"
                                name="price"
                                value={formData.price}
                                onChange={handleChange}
                                placeholder="3500"
                                min="0"
                                step="100"
                                style={{ width: '100%', padding: '12px', border: '1px solid #ddd', borderRadius: '8px' }}
                                required
                            />
                        </div>

                        <div>
                            <label style={{ display: 'block', marginBottom: '8px', fontWeight: '500' }}>Категория</label>
                            <select name="category" value={formData.category} onChange={handleChange} style={{ width: '100%', padding: '12px', border: '1px solid #ddd', borderRadius: '8px' }}>
                                {categories.map(cat => (
                                    <option key={cat.value} value={cat.value}>{cat.label}</option>
                                ))}
                            </select>
                        </div>
                    </div>

                    {/* Исправленные чекбоксы */}
                    <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px', marginBottom: '20px' }}>
                        <div>
                            <label style={{ display: 'flex', alignItems: 'center', gap: '10px', cursor: 'pointer' }}>
                                <input
                                    type="checkbox"
                                    name="is_hit"
                                    checked={formData.is_hit}  {/* всегда boolean */}
                                    onChange={handleChange}
                                />
                                Хит продаж
                            </label>
                        </div>
                        <div>
                            <label style={{ display: 'flex', alignItems: 'center', gap: '10px', cursor: 'pointer' }}>
                                <input
                                    type="checkbox"
                                    name="is_new"
                                    checked={formData.is_new}  {/* всегда boolean */}
                                    onChange={handleChange}
                                />
                                Новинка
                            </label>
                        </div>
                    </div>

                    <div style={{ marginBottom: '20px' }}>
                        <label style={{ display: 'block', marginBottom: '8px', fontWeight: '500' }}>Ссылка на изображение *</label>
                        <input
                            type="url"
                            name="image_url"
                            value={formData.image_url}
                            onChange={handleChange}
                            placeholder="https://example.com/image.jpg"
                            style={{ width: '100%', padding: '12px', border: '1px solid #ddd', borderRadius: '8px' }}
                            required
                        />
                        {formData.image_url && (
                            <div style={{ marginTop: '10px' }}>
                                <img src={formData.image_url} alt="Preview" width="100" style={{ borderRadius: '8px' }} />
                            </div>
                        )}
                    </div>

                    <div style={{ display: 'flex', gap: '15px', marginTop: '30px', paddingTop: '20px', borderTop: '1px solid #eee' }}>
                        <Button type="submit" disabled={saving}>
                            {saving ? 'Сохранение...' : (isEdit ? 'Сохранить изменения' : 'Создать букет')}
                        </Button>
                        <Button variant="outline" onClick={() => navigate('/admin/items')}>
                            Отмена
                        </Button>
                    </div>
                </form>
            </div>
        </div>
    );
}