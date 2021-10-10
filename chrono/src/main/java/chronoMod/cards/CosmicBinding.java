package chronoMod.cards;

import chronoMod.ChronoMod;
import chronoMod.actions.GainJadeAction;
import chronoMod.characters.Chronomancer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static chronoMod.ChronoMod.makeCardPath;

public class CosmicBinding extends AbstractXCostCard {
    private static final String CARD_ID = CosmicBinding.class.getSimpleName();
    public static final String ID = ChronoMod.makeID(CARD_ID);
    public static final String IMG = makeCardPath("attack/" + CARD_ID + ".png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Chronomancer.Enums.COLOR_BLUE;

    private static final int DAMAGE = 5;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int DEBUFF_AMOUNT = 1;
    private static final int UPGRADE_PLUS_DEBUFF = 1;

    public CosmicBinding() {
        super(ID, IMG, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.baseMagicNumber = DEBUFF_AMOUNT;
        this.magicNumber = this.baseMagicNumber;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = this.getEffectNum(p);
        if (effect > 0) {
            for (int i = 0; i < effect; i++) {
                this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageType,
                        AbstractGameAction.AttackEffect.POISON, true));
            }
        }
        if (effect >= 3) {
            this.addToBot(new GainJadeAction(1));
            this.addToBot(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false),
                    this.magicNumber, AbstractGameAction.AttackEffect.NONE));
            this.addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false),
                    this.magicNumber, AbstractGameAction.AttackEffect.NONE));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_DEBUFF);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new CosmicBinding();
    }
}
