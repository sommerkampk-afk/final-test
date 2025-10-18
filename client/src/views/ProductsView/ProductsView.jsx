import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import styles from './ProductsView.module.css';

export default function ProductsView() {
  const [cardView, setCardView] = useState(true);

  return (
    <div>
      <h2>FontAwesome demonstration</h2>
      <p>
        This code shows you how you can include Font Awesome icons on your page.
      </p>
      <p>
        Below are two icons: one to indicate a &quot;tile&quot; view of products, and
        another to indicate a &quot;table&quot; view.
      </p>
      <p>
        There&apos;s also a little bit of styling to get you started, but you can
        style it your own way.
      </p>
      <p>
        The state variable <code>cardView</code> tracks which view is the active
        one.
      </p>
      <FontAwesomeIcon
        icon="fa-solid fa-grip"
        className={`${styles.viewIcon} ${cardView ? styles.active : ''}`}
        onClick={() => setCardView(true)}
        title="View tiles"
      />
      <FontAwesomeIcon
        icon="fa-solid fa-table"
        className={`${styles.viewIcon} ${!cardView ? styles.active : ''}`}
        onClick={() => setCardView(false)}
        title="View table"
      />
    </div>
  );
}
